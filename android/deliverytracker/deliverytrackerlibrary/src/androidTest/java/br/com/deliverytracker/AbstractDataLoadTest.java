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

import org.junit.Assert;
import org.junit.Before;
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

    @Before
    public void before() throws Exception {
        try {
            final AtomicInteger aint = new AtomicInteger();
            aint.set(0);
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.addAuthStateListener(
                    new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                Log.i("login", "done!");
                                aint.set(1);
                            } else {
                                aint.set(-1);
                            }
                        }
                    });
            mAuth.signInWithEmailAndPassword(SENDER1_USER, SENDER1_PSWD);

            int val = aint.get();
            while (val == 0) {
                Thread.sleep(200);
                Log.i("wait", "wait");
                val = aint.get();
            }
            if (val == -1) {
                Assert.fail("We cant login in service!");
            }
            Log.i("initilization", "done!");
            this.loader = buildDataLoader(InstrumentationRegistry.getContext());
        } catch (Exception e) {
            Log.d(">>>>>", ">>>>", e);
            throw e;
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
        s.email = SENDER1_USER;
        loader.newSender(s);
        s.email = SENDER2_USER;
        loader.newSender(s);
//        // Context of the app under test.
//        Context ctx = InstrumentationRegistry.getTargetContext();
        Context ctx = InstrumentationRegistry.getContext();

        Log.d(this.getClass().getCanonicalName(), "blah");

        Log.d(this.getClass().getCanonicalName(), String.format("%s", loader));

        //assertEquals("br.com.deliverytracker.deliverytrackerlibrary.test", app.getPackageName());
    }
}
