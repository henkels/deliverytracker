package br.com.deliverytracker.receivingmanager.packageviewer;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.deliverytracker.receivingmanager.R;
import br.com.deliverytracker.receivingmanager.dao.DataLoader;
import br.com.deliverytracker.receivingmanager.dao.DataloaderFacory;
import br.com.deliverytracker.receivingmanager.packageviewer.PackageFragment.OnListFragmentInteractionListener;
import br.com.deliverytracker.receivingmanager.dao.IncommingPackage;

import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IncommingPackage} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PackageRecyclerViewAdapter extends RecyclerView.Adapter<PackageRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final OnListFragmentInteractionListener listener;

    private List<IncommingPackage> values;
    private boolean hasMore = false;
    private static final int NUM_ITENS = 50;

    private List<IncommingPackage> getValues() {
        if (values == null) {
            values = DataloaderFacory.getInstance(context).loadIncommingPackages(NUM_ITENS);
            hasMore = values.size() == NUM_ITENS;
        }
        return values;
    }

    public PackageRecyclerViewAdapter(Context context,OnListFragmentInteractionListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_package, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IncommingPackage item = getValues().get(position);
        holder.mItem = item;
        holder.mIdDescription.setText(item.getDescription());
        //holder.mSender.setText(item.getSender());
        //holder.mTransporter.setText(item.getTransporter());
        //holder.mSenddate.setText(new Date(item.getSenddate()).toString());
        //holder.mInitialEta.setText(new Date(item.getInitialEta()).toString());
        holder.mCurrentEta.setText(new Date(item.getInitialEta()).toString());
        //holder.mSender.setText(item.getSender());
        holder.mLastAtualizationTime.setText(new Date(item.getLastAtualizationTime()).toString());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getValues().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdDescription;
        //public final TextView mSender;
        //public final TextView mTransporter;
        //public final TextView mSenddate;
        //public final TextView mInitialEta;
        public final TextView mCurrentEta;
        // public final TextView mCurrentLocation;
        public final TextView mLastAtualizationTime;
        public IncommingPackage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdDescription = (TextView) view.findViewById(R.id.ipk_description);
            //mSender = (TextView) view.findViewById(R.id.ipk_sender);
            //mTransporter = (TextView) view.findViewById(R.id.ipk_transporter);
            //mSenddate = (TextView) view.findViewById(R.id.ipk_senddate);
            //mInitialEta = (TextView) view.findViewById(R.id.ipk_initialEta);
            mCurrentEta = (TextView) view.findViewById(R.id.ipk_currentEta);
            //mCurrentLocation = (TextView) view.findViewById(R.id.ipk_currentLocation);
            mLastAtualizationTime = (TextView) view.findViewById(R.id.ipk_lastAtualizationTime);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdDescription.getText() + "'";
        }
    }
}
