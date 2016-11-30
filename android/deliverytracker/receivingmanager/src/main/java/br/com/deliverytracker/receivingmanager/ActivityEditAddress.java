package br.com.deliverytracker.receivingmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.deliverytracker.dao.Address;
import br.com.deliverytracker.dao.Sender;

public class ActivityEditAddress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        final Address address = (Address) getIntent().getSerializableExtra(Address.PARAM_ID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.save_FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ret = new Intent();
                ret.putExtra(Address.PARAM_ID, address);
                setResult(Activity.RESULT_OK, ret);
                finish();
            }
        });


    }
}
