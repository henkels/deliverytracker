package br.com.deliverytracker.dao;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.atomic.AtomicReference;

import br.com.deliverytracker.dao.Firebase.DataLoaderImpl;
import br.com.deliverytracker.deliverytrackerlibrary.R;

//import br.com.deliverytracker.receivingmanager.dao.SqlLite.DataLoaderImpl;

/**
 * Created by tarcisio on 24/07/16.
 */
public class DataloaderFactory {

    public interface OnDataloaderDone {
        void DataloaderDone(DataLoader dataLoader);
    }

    private static final AtomicReference<DataLoader> DATALOADER_REF = new AtomicReference<>();

    public static void initInstance(Context context, String currentUser) {
        DATALOADER_REF.set(null);
        OnDataloaderDone dataloaderDone = new OnDataloaderDone() {
            @Override
            public void DataloaderDone(DataLoader dataLoader) {
                DATALOADER_REF.set(dataLoader);
            }
        };
        new DataLoaderImpl(context, "", currentUser, dataloaderDone);
    }

    public static DataLoader getInstance() {
        DataLoader ret = DATALOADER_REF.get();
        while (ret == null) {
            try {
                Thread.sleep(500);
                ret = DATALOADER_REF.get();
            } catch (Exception e) {
                Log.e(DataloaderFactory.class.getCanonicalName(), "Erro na inicialização do dataloader", e);
                //TODO qual é o valor adequado de exit?
                System.exit(1);
            }
        }
        return ret;
    }
}
