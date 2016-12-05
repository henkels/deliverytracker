package br.com.deliverytracker.receivingmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import br.com.deliverytracker.dao.Address;
import br.com.deliverytracker.dao.Sender;

public class ActivityRegisterReceiver extends AppCompatActivity {

    private static final int RC_EDIT_ADDRESS = 9003;

    private Sender current = null;

    private ArrayAdapter<Address> adapter;

    private ListView addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_receiver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        current = (Sender) getIntent().getSerializableExtra(Sender.PARAM_ID);

        addresses = (ListView) findViewById(R.id.addresses_LV);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.save_FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ret = new Intent();
                ret.putExtra(Sender.PARAM_ID, current);
                setResult(Activity.RESULT_OK, ret);
                finish();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.add_FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditAddress(new Address(), true);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.remove_FAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object o =
                addresses.getSelectedItem();
                if (o==null){
                    return;
                }

            }
        });


        TextView emailTV = (TextView) findViewById(R.id.email_TVR);

        emailTV.setText(current.email);

        EditText nameET = (EditText) findViewById(R.id.name_ET);

        nameET.setText(current.tryToGetName());

        ListView addressList = (ListView) findViewById(R.id.addresses_LV);

        adapter = new AddressListItemAdapter(this,
                R.layout.fragment_address_list, current.addresses);

        addressList.setAdapter(adapter);

//        Address address = new Address();
//        address.name = "CASA";
//        address.street = "Bruno Ruediger";
//        address.number = 1101;
//        address.neiburhood = "Velha Grande";
//        address.city = "Blumenau";
//        address.state = "Santa Catarina";
//        address.country = "Brasil";
//        address.latitude = 1d;
//        address.longitute = 1d;
//        address.postalCode = "89045-440";
//
//        adapter.add(address);
    }

    private void startEditAddress(Address address, boolean isNew) {
        Intent intent = new Intent(getApplicationContext(), ActivityEditAddress.class);
        intent.putExtra(Address.PARAM_ID, address);
        intent.putExtra(ActivityEditAddress.IS_NEW_FLAG_ID, isNew);
        startActivityForResult(intent, RC_EDIT_ADDRESS);
    }

    public static class AddressListItemAdapter extends ArrayAdapter<Address> {

        private final Context context;
        private final int layoutResourceId;
        //private final List<Address> data;

        public AddressListItemAdapter(Context context, int layoutResourceId, List<Address> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
        }

        private static final String SEP = ", ";
        private static final String SPACE = " ";
        //TODO translate
        private static final String NUMERO = "número";
        private static final String POSTAL_CODE = "CEP";
        private static final String GPS = "GPS";
        private static final String INFORMADO = "informado";
        private static final String NAO_INFORMADO = "não informado";

        private static void addString(StringBuilder sb, String prefix, String value) {
            if (value == null) {
                return;
            }
            value = value.trim();
            if (value.equals("")) {
                return;
            }
            if (prefix != null) {
                sb.append(prefix);
                sb.append(SPACE);
            }
            sb.append(value);
            sb.append(SEP);
        }

        private static void addString(StringBuilder sb, String value) {
            addString(sb, null, value);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Address address = getItem(position);
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
            }

            TextView tv = (TextView) row.findViewById(R.id.title_TV);
            String name = address.name == null ? "" : address.name;
            tv.setText(name);

            tv = (TextView) row.findViewById(R.id.content_TV);
            // Rua tal de tal, número 1, Bairro baixo, Blumenau, Santa Catarina, Brasil, CEP cep, GPS: informado
            StringBuilder sb = new StringBuilder();
            addString(sb, address.street);
            if (address.number != null) {
                addString(sb, NUMERO, address.number.toString());
            }
            addString(sb, address.neiburhood);
            addString(sb, address.city);
            addString(sb, address.country);
            addString(sb, POSTAL_CODE, address.postalCode);
            addString(sb, GPS, address.latitude != null && address.longitute != null ? INFORMADO : NAO_INFORMADO);
            sb.setLength(sb.length() - SEP.length());
            tv.setText(sb.toString());
            row.setTag(address);
            return row;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_EDIT_ADDRESS:
                if (resultCode == RESULT_OK) {
                    Address address = (Address) data.getSerializableExtra(Address.PARAM_ID);
                    current.addresses.add(address);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}