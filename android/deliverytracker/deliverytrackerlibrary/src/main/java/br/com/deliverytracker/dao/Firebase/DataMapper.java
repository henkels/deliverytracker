package br.com.deliverytracker.dao.Firebase;

/**
 * Created by tarcisio on 11/10/16.
 */

public interface DataMapper<SOURCE_DATA, TARGET_DATA> {
    TARGET_DATA map(SOURCE_DATA source);
}
