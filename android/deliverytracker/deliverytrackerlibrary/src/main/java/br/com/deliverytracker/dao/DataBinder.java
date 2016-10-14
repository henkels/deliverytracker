package br.com.deliverytracker.dao;

/**
 * Created by tarcisio on 11/10/16.
 */

public interface DataBinder<T> {
    void OnDataChange(DataBindOperation operation, T data);
}
