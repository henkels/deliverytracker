package br.com.deliverytracker.receivingmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class RegisterReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_receiver);
        ListView adresses_LV = (ListView) findViewById(R.id.adresses_LV);
    }
}
