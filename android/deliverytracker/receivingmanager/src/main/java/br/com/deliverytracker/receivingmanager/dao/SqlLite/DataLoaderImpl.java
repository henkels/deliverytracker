package br.com.deliverytracker.receivingmanager.dao.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import br.com.deliverytracker.dao.DataBinder;
import br.com.deliverytracker.dao.DataLoader;
import br.com.deliverytracker.dao.IncommingPackage;

/**
 * Created by tarcisio on 17/07/16.
 */
public class DataLoaderImpl extends SQLiteOpenHelper implements DataLoader {

    private static int V20160717 = 20160717;
    private static int V20160721 = 20160721;
    private static int V20161001 = 20161001;
    private static int VCURRENT = V20161001;

    private static final String DATABASE_NAME = "DeliveryTracker.Receiver";

    private static final String INCOMMING_PACKAGES_TABLE_NAME = "incomminPackages";

    public DataLoaderImpl(Context context) {

        super(context, DATABASE_NAME, null, VCURRENT);
        try {
            //// TODO: 19/07/16 colocar um populador
            populate(getWritableDatabase());
        } catch (Exception e) {
            //NOP
        }
    }

    private static void populate(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < 100; i++) {
            values.put("id", i);
            values.put("description", String.format("desc%d", i));
            values.put("sender", String.format("sender%d", i));
            values.put("transporter", String.format("transporter%d", i));
            values.put("senddate", System.currentTimeMillis());
            values.put("initialeta", System.currentTimeMillis());
            values.put("currenteta", System.currentTimeMillis());
            values.putNull("currentlocation");
            values.put("lastatualizationtime", System.currentTimeMillis());
            db.insert(INCOMMING_PACKAGES_TABLE_NAME, null, values);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table incomminPackages " +
                        "(id integer primary key, description text, sender text, transporter text, senddate INTEGER, initialeta INTEGER,  currenteta INTEGER, currentlocation blob, lastatualizationtime INTEGER)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL("drop table incomminPackages");
        } catch (Exception e) {

        }
        db.execSQL(
                "create table incomminPackages " +
                        "(id integer primary key, description text, sender text, transporter text, senddate INTEGER, initialeta INTEGER,  currenteta INTEGER, currentlocation blob, lastatualizationtime INTEGER)"

        );
    }

    private static final class IncommingPackageImpl implements IncommingPackage {

        private final int id;
        private final String description;
        private final String sender;
        private final String transporter;
        private final long senddate;
        private final long initialeta;
        private final long currenteta;
        private final Location currentlocation;
        private final long lastatualizationtime;

        IncommingPackageImpl(int id, String description, String sender, String transporter, long senddate, long initialeta, long currenteta, String currentlocation, long lastatualizationtime) {
            this.id = id;
            this.description = description;
            this.sender = sender;
            this.transporter = transporter;
            this.senddate = senddate;
            this.initialeta = initialeta;
            this.currenteta = currenteta;
            this.currentlocation = null;
            this.lastatualizationtime = lastatualizationtime;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getSender() {
            return sender;
        }

        @Override
        public String getTransporter() {
            return transporter;
        }

        @Override
        public long getSenddate() {
            return senddate;
        }

        @Override
        public long getInitialEta() {
            return initialeta;
        }

        @Override
        public long getCurrentEta() {
            return currenteta;
        }

        @Override
        public Location getCurrentLocation() {
            return currentlocation;
        }

        @Override
        public long getLastAtualizationTime() {
            return lastatualizationtime;
        }
    }

    @Override
    public List<IncommingPackage> loadIncommingPackages(int count) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(INCOMMING_PACKAGES_TABLE_NAME, null, null, null, null, null, null, Integer.toString(count));
        List<IncommingPackage> ret = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                ret.add(new IncommingPackageImpl(
                        c.getInt(0),
                        c.getString(1),//
                        c.getString(2),//
                        c.getString(3),//
                        c.getLong(4),//
                        c.getLong(5),//
                        c.getLong(6),//
                        c.getString(7),//
                        c.getLong(8)//
                ));
            }
        } finally {
            c.close();
        }
        return ret;
    }

    public void bind(DataBinder<IncommingPackage> dataBinder, int count) {
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


