package br.com.deliverytracker.firebase;

import org.junit.BeforeClass;

import br.com.deliverytracker.AbstractDataLoadTest;
import br.com.deliverytracker.dao.Firebase.DataLoaderImpl;

/**
 * Created by tarcisio on 14/10/16.
 */

public class FirebaseDataLoad extends AbstractDataLoadTest {

    //@BeforeClass
    public static void beforeClass(){
        loader = new DataLoaderImpl();
    }
}
