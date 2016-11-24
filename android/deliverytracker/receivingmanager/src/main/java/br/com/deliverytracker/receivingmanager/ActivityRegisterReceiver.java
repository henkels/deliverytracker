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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.deliverytracker.dao.Address;
import br.com.deliverytracker.dao.Sender;

public class ActivityRegisterReceiver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_receiver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Sender sender = (Sender) getIntent().getSerializableExtra(Sender.PARAM_ID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.saveFB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ret = new Intent();
                ret.putExtra(Sender.PARAM_ID, sender);
                setResult(Activity.RESULT_OK, ret);
                finish();
            }
        });


        TextView emailTV = (TextView) findViewById(R.id.email_TVR);

        emailTV.setText(sender.email);

        ListView addressList = (ListView) findViewById(R.id.addresses_LV);

        ArrayAdapter<Address> adapter = new AddressListItemAdapter(this,
                R.layout.fragment_address_list, sender.addresses);

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
            tv.setText(address.name);

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

}
