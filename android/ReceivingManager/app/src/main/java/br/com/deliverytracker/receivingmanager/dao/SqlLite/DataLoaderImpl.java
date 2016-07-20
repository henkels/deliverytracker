package br.com.deliverytracker.receivingmanager.dao.SqlLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import java.sql.Timestamp;
import java.util.ArrayList;
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
        //// TODO: 19/07/16 colocar um populador 
    }

    private static final String LIMIT = "50";

    private static final String INCOMMING_PACKAGES_TABLE_NAME = "incomminPackages";

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

    private static final class IncommingPackageImpl implements IncommingPackage {

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public String getSender() {
            return null;
        }

        @Override
        public String getTransporter() {
            return null;
        }

        @Override
        public Timestamp getSenddate() {
            return null;
        }

        @Override
        public Timestamp getInitialEta() {
            return null;
        }

        @Override
        public Timestamp getCurrentEta() {
            return null;
        }

        @Override
        public Location getCurrentLocation() {
            return null;
        }

        @Override
        public Timestamp getLastAtualizationTime() {
            return null;
        }
    }

    @Override
    public List<IncommingPackage> loadIncommingPackages(int count) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(INCOMMING_PACKAGES_TABLE_NAME, null, null, null, null, null, null, LIMIT);
        List<IncommingPackage> ret = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                ret.add(new IncommingPackageImpl());
            }
        } finally {
            c.close();
        }
        return ret;
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


