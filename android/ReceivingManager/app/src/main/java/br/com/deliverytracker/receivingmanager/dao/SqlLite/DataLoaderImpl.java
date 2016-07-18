package br.com.deliverytracker.receivingmanager.dao.SqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import java.sql.Timestamp;
import java.util.List;

import br.com.deliverytracker.receivingmanager.dao.DataLoader;
import br.com.deliverytracker.receivingmanager.dao.IncommingPackage;

/**
 * Created by tarcisio on 17/07/16.
 */
public class DataLoaderImpl extends SQLiteOpenHelper implements DataLoader {

    private static final String DATABASE_NAME = "DeliveryTracker.Receiver";

    private static int V20160717 = 20160717;
    private static int VCURRENT = V20160717;

    public DataLoaderImpl(Context context) {
        super(context, DATABASE_NAME, null, VCURRENT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table incomminPackages " +
                        "(id integer primary key, description text, sender text, transporter text, senddate text, initialeta text,  currenteta text, currentlocation blob, lastatualizationtime text)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public List<IncommingPackage> loadIncommingPackages(int count) {
        SQLiteDatabase db = getWritableDatabase();
        throw new RuntimeException();

    }


//    DataLoaderImpl(Context context)
//    {
//        super(context, DATABASE_NAME , null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        xxx
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        xxx
//    }
//
//    @Override
//    public List<IncommingPackage> loadIncommingPackages(int count) {
//        return null;
//    }
}


