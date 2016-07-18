package br.com.deliverytracker.receivingmanager.dao;

import android.location.Location;

import java.sql.Timestamp;

/**
 * Created by tarcisio on 15/07/16.
 */
public interface IncommingPackage {

    public String getDescription();

    public String getSender();

    public String getTransporter();

    public Timestamp getSenddate();

    public Timestamp getInitialEta();

    public Timestamp getCurrentEta();

    public Location getCurrentLocation();

    public Timestamp getLastAtualizationTime();
}
