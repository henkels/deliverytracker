package br.com.deliverytracker.dao.Firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import br.com.deliverytracker.dao.DataBindOperation;
import br.com.deliverytracker.dao.DataBinder;

/**
 * Created by tarcisio on 11/10/16.
 */

public class DataBinderAdapter<SOURCE_DATA, TARGET_DATA> implements ChildEventListener {

    private final Class<SOURCE_DATA> clazz;
    private final DataBinder<TARGET_DATA> dataBinder;
    private final DataMapper<SOURCE_DATA, TARGET_DATA> mapper;

    DataBinderAdapter(Query query, final Class<SOURCE_DATA> clazz, DataMapper<SOURCE_DATA, TARGET_DATA> mapper, DataBinder<TARGET_DATA> dataBinder) {
        this.clazz = clazz;
        this.dataBinder = dataBinder;
        this.mapper = mapper;
        query.addChildEventListener(this);
    }

    private TARGET_DATA getCurrent(DataSnapshot dataSnapshot) {
        try {
            SOURCE_DATA source = dataSnapshot.getValue(clazz);
            return mapper.map(source);
        } catch (Exception e) {
            Log.e("erro", "error", e);
            throw e;
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        dataBinder.OnDataChange(DataBindOperation.LOAD, getCurrent(dataSnapshot));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        dataBinder.OnDataChange(DataBindOperation.RELOAD, getCurrent(dataSnapshot));
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        dataBinder.OnDataChange(DataBindOperation.UNLOAD, getCurrent(dataSnapshot));
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        dataBinder.OnDataChange(DataBindOperation.RELOAD, getCurrent(dataSnapshot));
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
    }

}
