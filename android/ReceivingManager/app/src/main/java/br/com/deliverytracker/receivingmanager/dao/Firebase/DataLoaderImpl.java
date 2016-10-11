package br.com.deliverytracker.receivingmanager.dao.Firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.deliverytracker.commom.data.LocationData;
import br.com.deliverytracker.commom.data.Package;
import br.com.deliverytracker.receivingmanager.dao.DataLoader;
import br.com.deliverytracker.receivingmanager.dao.IncommingPackage;

/**
 * Created by tarcisio on 10/10/16.
 */

public class DataLoaderImpl implements DataLoader {

    private static final String PACKAGES_NODE = "packages";

    private DatabaseReference mDatabase;


    public DataLoaderImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //pubulate the database
        //String key = mDatabase.child(PACKAGES_NODE).push().getKey();
        Package _package = new Package();

                _package. description="desc";
        _package.sender = "sender1";
        _package.transporter="tr1";
        _package.senddate=0;
        _package.initialEta=1;
        _package. currentEta=2;
        _package. location=new LocationData();
        _package. lastAtualizationTime=3;

        //Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put("/posts/" + key, postValues);
        childUpdates.put(PACKAGES_NODE, _package);

        mDatabase.updateChildren(childUpdates);    }

    public List<IncommingPackage> loadIncommingPackages(int count) {
        Query query = mDatabase.child(PACKAGES_NODE)//
                .limitToFirst(count)//

         .addListenerForSingleValueEvent(new ChildEventListener() {
             public void onChildAdded(DataSnapshot var1, String var2){};

             public void onChildChanged(DataSnapshot var1, String var2){};

             public void onChildRemoved(DataSnapshot var1){};

             public void onChildMoved(DataSnapshot var1, String var2){};

             public void onCancelled(DatabaseError var1){};

         });

        return new ArrayList<>();

    }

}
