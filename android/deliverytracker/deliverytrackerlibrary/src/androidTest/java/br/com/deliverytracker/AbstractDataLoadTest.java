package br.com.deliverytracker;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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

    private static final String SENDER1_USER = "sender1test@deliverytracker.com";
    private static final String SENDER1_PSWD = "sender1test@deliverytracker.com";
    private static final String SENDER2_USER = "sender2test@deliverytracker.com";
    private static final String SENDER2_PSWD = "sender2test@deliverytracker.com";

    private DataLoader loader;

    protected AbstractDataLoadTest() {
        super();
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        try {
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(SENDER1_USER, SENDER1_PSWD);
            Log.i("initilization", "done!");
        } catch (Exception e) {
            Log.d(">>>>>", ">>>>", e);
            throw e;
        }
    }

    private void checkLoader() {
        if (loader == null) {
            try {
                this.loader = buildDataLoader(InstrumentationRegistry.getContext());
            } catch (Exception e) {
                Log.d(">>>>>", ">>>>", e);
                throw e;
            }
        }
    }

    @Before
    public void before() throws Exception {
        checkLoader();
    }

    @After
    public void after() {
        if (loader != null) {
            loader.waitForPendingOperations(5000);
            //cleanDataLoader(loader);
        }
    }

    protected abstract DataLoader buildDataLoader(Context ctx);

    protected abstract void cleanDataLoader(DataLoader dataLoader);

    @Test
    public void testAddSender01() throws Exception {
        Sender s = new Sender();
        s.email = SENDER1_USER;
        loader.newSender(s);
        s.email = SENDER2_USER;
        loader.newSender(s);
    }

    @Ignore
    @Test
    public void testAddSender02() throws Exception {
        Sender s = new Sender();
        s.email = SENDER1_USER;
        loader.newSender(s);
        try {
            loader.newSender(s);
            fail("Não detectou email já usado");
        } catch (Exception e)  {

        }

    }
}
