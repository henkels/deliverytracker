package br.com.deliverytracker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Toast;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.deliverytracker.dao.DataLoader;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public abstract class AbstractDataLoadTest {

    protected static DataLoader loader;


    @Test
    public void useAppContext() throws Exception {

        // Context of the app under test.
        Context ctx = InstrumentationRegistry.getTargetContext();

        Toast.makeText(ctx, "blah", Toast.LENGTH_SHORT).show();

        System.out.print(loader);
        Toast.makeText(ctx, String.format("%s", loader), Toast.LENGTH_SHORT).show();

        assertEquals("br.com.deliverytracker.deliverytrackerlibrary.test", ctx.getPackageName());
    }
}
