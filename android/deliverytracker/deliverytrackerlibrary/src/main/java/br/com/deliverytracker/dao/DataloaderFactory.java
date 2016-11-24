package br.com.deliverytracker.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.deliverytracker.dao.Firebase.DataLoaderImpl;

//import br.com.deliverytracker.receivingmanager.dao.SqlLite.DataLoaderImpl;

/**
 * Created by tarcisio on 24/07/16.
 */
public class DataloaderFactory {

    public interface OnDataloaderDone {
        void onDataloaderDone(DataLoader dataLoader);
    }

    private static final List<OnDataloaderDone> ON_DATALOADER_DONE_LIST = new ArrayList<>();

    private static DataLoader DATA_LOADER = null;

    private static final OnDataloaderDone ON_DATA_LOADER_DONE = new OnDataloaderDone() {
        @Override
        public void onDataloaderDone(DataLoader dataLoader) {
            synchronized (ON_DATALOADER_DONE_LIST) {
                DATA_LOADER = dataLoader;
                updateOnDataLoaders();
            }

        }
    };


    private static void updateOnDataLoaders() {
        for (OnDataloaderDone dataloaderDone : ON_DATALOADER_DONE_LIST) {
            dataloaderDone.onDataloaderDone(DATA_LOADER);
        }
    }

    public static void initInstance(Context context, String currentUser) {
        synchronized (ON_DATALOADER_DONE_LIST) {
            DATA_LOADER = null;
            updateOnDataLoaders();
            new DataLoaderImpl(context, "", currentUser, ON_DATA_LOADER_DONE);
        }
    }

    public static void registerOnDataloaderDone(OnDataloaderDone onDataloaderDone) {
        synchronized (ON_DATALOADER_DONE_LIST) {
            ON_DATALOADER_DONE_LIST.add(onDataloaderDone);
            if (DATA_LOADER != null) {
                onDataloaderDone.onDataloaderDone(DATA_LOADER);
            }
        }
    }

    public static void unregisterOnDataloaderDone(OnDataloaderDone onDataloaderDone) {
        synchronized (ON_DATALOADER_DONE_LIST) {
            ON_DATALOADER_DONE_LIST.remove(onDataloaderDone);
            onDataloaderDone.onDataloaderDone(null);
        }
    }

//    public static DataLoader getInstance() {
//        DataLoader ret = dataLoader;
//        while (ret == null) {
//            try {
//                Thread.sleep(500);
//                ret = dataLoader;
//            } catch (Exception e) {
//                Log.e(DataloaderFactory.class.getCanonicalName(), "Erro na inicialização do dataloader", e);
//                //TODO qual é o valor adequado de exit?
//                System.exit(1);
//            }
//        }
//        return ret;
//    }
}
