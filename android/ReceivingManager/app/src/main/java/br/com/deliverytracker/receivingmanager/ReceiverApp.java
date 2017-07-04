package br.com.deliverytracker.receivingmanager;

import android.app.Application;
import android.content.Context;

/**
 * Created by tarcisio on 01/07/17.
 */

public class ReceiverApp extends Application {

    public ReceiverApp() {
        super();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
