package br.com.deliverytracker.firebase;

import android.content.Context;
import android.content.res.Resources;
import android.test.mock.MockContext;
import android.test.mock.MockResources;
import android.util.Log;

import com.google.firebase.FirebaseApp;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.deliverytracker.AbstractDataLoadTest;
import br.com.deliverytracker.dao.DataLoader;
import br.com.deliverytracker.dao.DataloaderFactory;
import br.com.deliverytracker.dao.Firebase.DataLoaderImpl;
import br.com.deliverytracker.deliverytrackerlibrary.R;

/**
 * Created by tarcisio on 14/10/16.
 */

public class FirebaseDataLoadTest extends AbstractDataLoadTest {

    private String fb_ctx = "";

    protected DataLoader buildDataLoader(Context ctx) {
        FirebaseApp.initializeApp(ctx);
        fb_ctx = ctx.getString(R.string.fb_ctx);
        final AtomicBoolean toWait = new AtomicBoolean();
        toWait.set(true);
        DataLoader ret = new DataLoaderImpl(ctx, fb_ctx + "/", SENDER1_USER, new DataloaderFactory.OnDataloaderDone() {
            @Override
            public void DataloaderDone(DataLoader dataLoader) {
                toWait.set(false);
            }
        });
        while (toWait.get()) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                Log.e("test", "Erro na inicialização", e);
                toWait.set(false);
            }
        }
        return ret;
    }

    @Override
    protected void cleanDataLoader(DataLoader dataLoader) {
        ((DataLoaderImpl) dataLoader).cleanNode(fb_ctx);
    }
}