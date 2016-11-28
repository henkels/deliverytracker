package br.com.deliverytracker.dao;


import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

import br.com.deliverytracker.deliverytrackerlibrary.StringUtils;

/**
 * Created by tarcisio on 22/10/16.
 */

public class Sender extends MapeableObject {

    @Exclude
    public static final String PARAM_ID = Sender.class.getCanonicalName();

    public String email;
    public String id;
    public String name;
    public List<Address> addresses = new ArrayList<>();

    public String tryToGetName() {
        if (name != null) {
            return name;
        }
        if (email == null) {
            return "";
        }
        return StringUtils.emailToName(email);
    }
}
