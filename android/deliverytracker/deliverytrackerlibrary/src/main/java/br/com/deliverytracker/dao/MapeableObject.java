package br.com.deliverytracker.dao;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tarcisio on 22/10/16.
 */

public class MapeableObject  implements Serializable {


    @Exclude
    public static final long serialVersionUID = 1L;

    public Map<String, Object> toMap() {
        Map<String, Object> ret = new HashMap<>();
        for (Field field : this.getClass().getFields()) {
            try {
                if (field.isAnnotationPresent(Exclude.class)) {
                    continue;
                }
                Object value = field.get(this);
                if (value != null) {
                    ret.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {

            }
        }
        return ret;
    }
}
