package br.com.deliverytracker;

import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.deliverytracker.dao.DataBindOperation;
import br.com.deliverytracker.dao.DataBinder;
import br.com.deliverytracker.dao.DataLoader;
import br.com.deliverytracker.dao.IncommingPackage;
import br.com.deliverytracker.dao.Sender;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public abstract class AbstractDataLoadTest {

    private DataLoader loader;

    protected AbstractDataLoadTest() {
        super();
    }

    @Before
    public void before() {
        try {
            this.loader = buildDataLoader(InstrumentationRegistry.getContext());
        } catch (Exception e) {
            Log.d(">>>>>", ">>>>", e);
        }
    }

    protected abstract DataLoader buildDataLoader(Context ctx);

    @Test
    public void testAddSender() throws Exception {

        loader.bind(new DataBinder<IncommingPackage>() {
            @Override
            public void OnDataChange(DataBindOperation operation, IncommingPackage data) {
                Log.d(operation.name(), data.toString());
            }
        }, 10);
        Sender s = new Sender();
        s.email = "madrugao@h.com.br";
        loader.newSender(s);
//        // Context of the app under test.
//        Context ctx = InstrumentationRegistry.getTargetContext();
        Context ctx = InstrumentationRegistry.getContext();

        Log.d(this.getClass().getCanonicalName(), "blah");

        Log.d(this.getClass().getCanonicalName(), String.format("%s", loader));

        //assertEquals("br.com.deliverytracker.deliverytrackerlibrary.test", app.getPackageName());
    }
}
