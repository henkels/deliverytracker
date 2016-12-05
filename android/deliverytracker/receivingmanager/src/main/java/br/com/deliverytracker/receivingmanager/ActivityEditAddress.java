package br.com.deliverytracker.receivingmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.com.deliverytracker.dao.Address;
import br.com.deliverytracker.dao.Sender;

public class ActivityEditAddress extends AppCompatActivity {

    public static final String IS_NEW_FLAG_ID = "is_new";

    private EditText endName_ET;

    private EditText localization_ET;

    private EditText country_ET;

    private EditText cep_ET;

    private EditText state_ET;

    private EditText city_ET;

    private EditText street_ET;

    private EditText number_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        Intent intent = getIntent();
        final Address address = (Address) intent.getSerializableExtra(Address.PARAM_ID);
        final boolean isNew = intent.getBooleanExtra(IS_NEW_FLAG_ID, false);


        endName_ET = (EditText) findViewById(R.id.endName_ET);
        localization_ET = (EditText) findViewById(R.id.localization_ET);
        country_ET = (EditText) findViewById(R.id.country_ET);
        cep_ET = (EditText) findViewById(R.id.cep_ET);
        state_ET = (EditText) findViewById(R.id.state_ET);
        city_ET = (EditText) findViewById(R.id.city_ET);
        street_ET = (EditText) findViewById(R.id.street_ET);
        number_ET = (EditText) findViewById(R.id.number_ET);

        endName_ET.setText(address.name);
        //localization_ET.setText(
        country_ET.setText(address.country);
        cep_ET.setText(address.postalCode);
        state_ET.setText(address.state);
        city_ET.setText(address.city);
        street_ET.setText(address.street);
        String number = "";
        if (address.number != null) {
            number = address.number.toString();
        }
        number_ET.setText(number);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.save_FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address.name = endName_ET.getText().toString();
                //localization_ET.setText(
                address.country = country_ET.getText().toString();
                address.postalCode = cep_ET.getText().toString();
                address.state = state_ET.getText().toString();
                address.city = city_ET.getText().toString();
                address.street = street_ET.getText().toString();
                int number = 0;
                try {
                    number = Integer.parseInt(number_ET.getText().toString());
                } catch (Exception e) {
                }
                address.number = number;
                Intent ret = new Intent();
                ret.putExtra(Address.PARAM_ID, address);
                ret.putExtra(IS_NEW_FLAG_ID, isNew);
                setResult(Activity.RESULT_OK, ret);
                finish();
            }
        });


    }
}
