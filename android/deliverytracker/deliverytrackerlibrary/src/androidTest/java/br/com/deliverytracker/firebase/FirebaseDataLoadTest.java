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

/**
 * Created by tarcisio on 14/10/16.
 */

public class FirebaseDataLoadTest extends AbstractDataLoadTest {

    protected DataLoader buildDataLoader(Context ctx) {
        FirebaseApp.initializeApp(ctx);
        return new DataLoaderImpl();
    }
}