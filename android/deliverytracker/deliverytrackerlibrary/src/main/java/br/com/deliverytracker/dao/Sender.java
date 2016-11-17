package br.com.deliverytracker.dao;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarcisio on 22/10/16.
 */

public class Sender extends MapeableObject implements Serializable {

    public static final String PARAM_ID = Sender.class.getCanonicalName();

    public String email;
    public String id;
    public String name;
    public List<Address> addresses = new ArrayList<>();
}
