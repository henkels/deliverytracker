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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });

        Sender sender = (Sender) getIntent().getSerializableExtra(Sender.PARAM_ID);

        TextView emailTV = (TextView) findViewById(R.id.email_TVR);

        emailTV.setText(sender.email);

        ListView addressList = (ListView) findViewById(R.id.addresses_LV);

        ArrayAdapter<Address> adapter = new AddressListItemAdapter(this,
                R.layout.fragment_address_list, sender.addresses);

        addressList.setAdapter(adapter);

        Address address = new Address();
        address.name = "A1";
        address.postalCode = "89045-440";

        adapter.add(address);
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
            tv.setText(address.toString());
            row.setTag(address);
            return row;
        }

    }

}
