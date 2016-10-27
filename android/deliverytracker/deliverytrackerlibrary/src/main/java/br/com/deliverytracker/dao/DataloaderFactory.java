package br.com.deliverytracker.dao;

import android.content.Context;

import br.com.deliverytracker.dao.Firebase.DataLoaderImpl;
import br.com.deliverytracker.deliverytrackerlibrary.R;

//import br.com.deliverytracker.receivingmanager.dao.SqlLite.DataLoaderImpl;

/**
 * Created by tarcisio on 24/07/16.
 */
public class DataloaderFactory {

    public static DataLoader getInstance(Context context) {
        return new DataLoaderImpl();
    }
}
