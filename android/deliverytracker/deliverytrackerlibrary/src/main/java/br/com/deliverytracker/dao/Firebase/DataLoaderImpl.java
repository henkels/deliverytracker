package br.com.deliverytracker.dao.Firebase;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

import br.com.deliverytracker.commom.data.LocationData;
import br.com.deliverytracker.commom.data.Package;
import br.com.deliverytracker.dao.DataBinder;
import br.com.deliverytracker.dao.DataLoader;
import br.com.deliverytracker.dao.DataloaderFactory;
import br.com.deliverytracker.dao.IncommingPackage;
import br.com.deliverytracker.dao.Sender;

/**
 * Created by tarcisio on 10/10/16.
 */

public class DataLoaderImpl implements DataLoader {

    private static final String PACKAGES_SUFFIX = "packages";
    private final String PACKAGES_NODE;

    private static final String SENDERS_SUFFIX = "senders";
    private final String SENDERS_NODE;

    private static final String EMAIL_TO_ID_SUFFIX = "email_to_id";
    private final String EMAIL_TO_ID_NODE;

    private static final String EMAIL_SENDER_KEY = "current_key";

    private String userid;



    private DatabaseReference mDatabase;
    private final AtomicInteger pendingOperationsCount = new AtomicInteger();

    public DataLoaderImpl(Context context, String dataCtx, String currentUser, DataloaderFactory.OnDataloaderDone dataloaderDone) {
        FirebaseApp.initializeApp(context);

        PACKAGES_NODE = dataCtx + PACKAGES_SUFFIX;
        SENDERS_NODE = dataCtx + SENDERS_SUFFIX;
        EMAIL_TO_ID_NODE = dataCtx + EMAIL_TO_ID_SUFFIX;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadUserId(normalizeEmail(currentUser), dataloaderDone);
    }

    private void loadUserId(String currentUser, final DataloaderFactory.OnDataloaderDone dataloaderDone) {
        DatabaseReference ref =  mDatabase.child(EMAIL_TO_ID_NODE).child(currentUser);
        ref.addCo
        mDatabase.child(EMAIL_TO_ID_NODE).child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                if (value == null) {
                    userid = null;
                } else {
                    userid = value.toString();
                }
                dataloaderDone.DataloaderDone(DataLoaderImpl.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userid = null;
                dataloaderDone.DataloaderDone(DataLoaderImpl.this);
            }
        });
    }

    @Override
    public void waitForPendingOperations(long timeout) {
        long zero = System.currentTimeMillis();
        while (pendingOperationsCount.get() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (System.currentTimeMillis() - zero > 5000) {
                throw new RuntimeException("Timeout");
            }
        }
    }

    public List<IncommingPackage> loadIncommingPackages(int count) {
        List<IncommingPackage> ret = new ArrayList<>();
        Query query = mDatabase.child(PACKAGES_NODE)//
                .limitToFirst(count);//
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Package _package = dataSnapshot.getValue(Package.class);
                IncommingPackage incommingPackage = new IncommingPackage() {
                    @Override
                    public String getDescription() {
                        return _package.description;
                    }

                    @Override
                    public String getSender() {
                        return _package.sender;
                    }

                    @Override
                    public String getTransporter() {
                        return _package.transporter;
                    }

                    @Override
                    public long getSenddate() {
                        return _package.senddate;
                    }

                    @Override
                    public long getInitialEta() {
                        return _package.initialEta;
                    }

                    @Override
                    public long getCurrentEta() {
                        return _package.currentEta;
                    }

                    @Override
                    public Location getCurrentLocation() {
                        return null;
                    }

                    @Override
                    public long getLastAtualizationTime() {
                        return _package.lastAtualizationTime;
                    }
                };
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return ret;

    }

    public void bind(DataBinder<IncommingPackage> dataBinder, int count) {
        Query query = mDatabase.child(PACKAGES_NODE)//
                .limitToFirst(count);//
        new DataBinderAdapter(query, Package.class, PackageToIncommingPackageDataMapper.INSTANCE, dataBinder);
    }

    private void doUpdates(Map<String, Object> updates) {
        pendingOperationsCount.incrementAndGet();
        Task task =
                mDatabase.updateChildren(updates);
        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pendingOperationsCount.decrementAndGet();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("doUpdates", "onFailure: ", e);
            }
        });
    }

    private static String normalizeEmail(String email) {
        return email.replace('.', '!');
    }

    private static String unNormalizeEmail(String email) {
        return email.replace('!', '.');
    }

    // Sender
    public void newSender(Sender sender) {
        String key = mDatabase.child(SENDERS_NODE).push().getKey();
        Map<String, Object> senderValues = sender.toMap();
        Map<String, Object> emailToSenderValues = new HashMap<>();
        emailToSenderValues.put(EMAIL_SENDER_KEY, key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + SENDERS_NODE + "/" + key, senderValues);
        childUpdates.put("/" + EMAIL_TO_ID_NODE + "/" + normalizeEmail(sender.email), key);
        doUpdates(childUpdates);
    }

    public void cleanNode(String node) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + node, new HashMap<>());
        doUpdates(childUpdates);
    }

//    <T> public void bind(DataBinder<T> dataBinder, Class<T> clazz, int count) {
//        Query query = mDatabase.child(PACKAGES_NODE)//
//                .limitToFirst(count);//
//        new DataBinderAdapter(query, Package.class, PackageToIncommingPackageDataMapper.INSTANCE, dataBinder);
//    }


}
