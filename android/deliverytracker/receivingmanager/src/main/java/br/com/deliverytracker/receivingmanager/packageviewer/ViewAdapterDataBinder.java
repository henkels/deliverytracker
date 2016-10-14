package br.com.deliverytracker.receivingmanager.packageviewer;

import java.util.List;

import br.com.deliverytracker.dao.DataBindOperation;
import br.com.deliverytracker.dao.DataBinder;

/**
 * Created by tarcisio on 11/10/16.
 */

public class ViewAdapterDataBinder<T> implements DataBinder<T> {

    interface OnDataChangeListner {
        void changed();
    }

    private final List<T> list;
    private final OnDataChangeListner listner;

    public ViewAdapterDataBinder(List<T> list, OnDataChangeListner listner) {
        this.list = list;
        this.listner = listner;
    }

    public void OnDataChange(DataBindOperation operation, T data) {
        switch (operation) {
            case LOAD:
                list.add(data);
                break;
            case UNLOAD:
                list.remove(data);
                break;
            case RELOAD:
                list.remove(data);
                list.add(data);
                break;
        }
        listner.changed();
    }
}
