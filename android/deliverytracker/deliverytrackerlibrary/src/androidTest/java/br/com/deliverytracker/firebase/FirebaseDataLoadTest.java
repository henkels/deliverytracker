package br.com.deliverytracker.firebase;

import android.content.Context;
import android.content.res.Resources;
import android.test.mock.MockContext;
import android.test.mock.MockResources;

import com.google.firebase.FirebaseApp;

import org.junit.Test;

import br.com.deliverytracker.AbstractDataLoadTest;
import br.com.deliverytracker.dao.DataLoader;
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
        return new DataLoaderImpl(fb_ctx + "/");
    }

    @Override
    protected void cleanDataLoader(DataLoader dataLoader) {
        ((DataLoaderImpl) dataLoader).cleanNode(fb_ctx);
    }
}