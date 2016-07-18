package br.com.deliverytracker.receivingmanager.packageviewer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.deliverytracker.receivingmanager.R;
import br.com.deliverytracker.receivingmanager.dao.DataLoader;
import br.com.deliverytracker.receivingmanager.packageviewer.PackageFragment.OnListFragmentInteractionListener;
import br.com.deliverytracker.receivingmanager.dao.IncommingPackage;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IncommingPackage} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PackageRecyclerViewAdapter extends RecyclerView.Adapter<PackageRecyclerViewAdapter.ViewHolder> {

    private final DataLoader dataLoader;
    private final OnListFragmentInteractionListener listener;

    private List<IncommingPackage> _values;
    private boolean hasMore = false;
    private static final int NUM_ITENS = 50;

    private List<IncommingPackage> getValues(){
        if (_values==null){
          _values =  dataLoader.loadIncommingPackages(NUM_ITENS);
            hasMore = _values.size() == NUM_ITENS;
        }
        return _values;
    }

    public PackageRecyclerViewAdapter(DataLoader dataLoader, OnListFragmentInteractionListener listener) {
        this.dataLoader = dataLoader;
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
     IncommingPackage item =  getValues().get(position);
        holder.mItem = item;
        holder.mIdDescription.setText(item.getDescription());
        holder.mSender.setText(item.getSender());

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
        public final TextView mSender;
        public IncommingPackage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdDescription = (TextView) view.findViewById(R.id.ipk_description);
            mSender = (TextView) view.findViewById(R.id.ipk_sender);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdDescription.getText() + "'";
        }
    }
}
