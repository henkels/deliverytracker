package br.com.deliverytracker.dao;

import com.google.firebase.database.Exclude;

/**
 * Created by tarcisio on 15/11/16.
 */

public class Address extends MapeableObject {

    @Exclude
    public static final String PARAM_ID = Address.class.getCanonicalName();

    public String name;
    public String country;
    public String postalCode;
    public String state;
    public String city;
    public String neiburhood;
    public String street;
    public Integer number;
    public Double latitude;
    public Double longitute;
//    Localização
//            CEP
//    Pais
//            Estado
//    Cidade
//            Rua
//    N°
}
