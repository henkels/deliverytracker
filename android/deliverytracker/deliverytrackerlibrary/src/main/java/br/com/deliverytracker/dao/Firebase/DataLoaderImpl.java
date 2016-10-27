package br.com.deliverytracker.dao.Firebase;

import android.location.Location;

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

import br.com.deliverytracker.commom.data.LocationData;
import br.com.deliverytracker.commom.data.Package;
import br.com.deliverytracker.dao.DataBinder;
import br.com.deliverytracker.dao.DataLoader;
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


    private DatabaseReference mDatabase;

    public DataLoaderImpl(String ctx) {
        PACKAGES_NODE = ctx + PACKAGES_SUFFIX;
        SENDERS_NODE = ctx + SENDERS_SUFFIX;

        mDatabase = FirebaseDatabase.getInstance().getReference();
//        //populate the database
//        //String key = mDatabase.child(PACKAGES_NODE).push().getKey();
//        Package _package = new Package();
//
//        _package.description = "desc";
//        _package.sender = "sender1";
//        _package.transporter = "tr1";
//        _package.senddate = 0;
//        _package.initialEta = 1;
//        _package.currentEta = 2;
//        _package.location = new LocationData();
//        _package.lastAtualizationTime = 3;
//
//        //Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        List<Package> packages = new ArrayList<>();
//        packages.add(_package);
//        //childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put(PACKAGES_NODE, packages);
//
//        mDatabase.updateChildren(childUpdates);
//
//        String key = mDatabase.child(PACKAGES_NODE).push().getKey();
//        childUpdates.clear();
//        childUpdates.put(PACKAGES_NODE + "/" + key, _package);
//        mDatabase.updateChildren(childUpdates);
//
    }

    public DataLoaderImpl() {
        this("");
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

    // Sender
    public void newSender(Sender sender) {
        String key = mDatabase.child(SENDERS_NODE).push().getKey();
        Map<String, Object> postValues = sender.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + SENDERS_NODE + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    public void cleanNode(String node) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + node, new HashMap<>());
        mDatabase.updateChildren(childUpdates);
    }

//    <T> public void bind(DataBinder<T> dataBinder, Class<T> clazz, int count) {
//        Query query = mDatabase.child(PACKAGES_NODE)//
//                .limitToFirst(count);//
//        new DataBinderAdapter(query, Package.class, PackageToIncommingPackageDataMapper.INSTANCE, dataBinder);
//    }


}
