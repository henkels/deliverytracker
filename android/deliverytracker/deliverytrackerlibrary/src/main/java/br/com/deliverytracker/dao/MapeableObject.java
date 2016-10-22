package br.com.deliverytracker.dao;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tarcisio on 22/10/16.
 */

public class MapeableObject {

    public Map<String, Object> toMap() {
        Map<String, Object> ret = new HashMap<>();
        for (Field field : this.getClass().getFields()) {
            try {
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
