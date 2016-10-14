package br.com.deliverytracker.dao;

import android.location.Location;

import java.sql.Timestamp;

/**
 * Created by tarcisio on 15/07/16.
 */
public interface IncommingPackage {

    public String getDescription();

    public String getSender();

    public String getTransporter();

    public long getSenddate();

    public long getInitialEta();

    public long getCurrentEta();

    public Location getCurrentLocation();

    public long getLastAtualizationTime();
}
