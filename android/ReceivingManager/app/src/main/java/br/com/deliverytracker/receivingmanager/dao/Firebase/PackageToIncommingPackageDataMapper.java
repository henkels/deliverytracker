package br.com.deliverytracker.receivingmanager.dao.Firebase;

import android.location.Location;

import br.com.deliverytracker.commom.data.Package;
import br.com.deliverytracker.receivingmanager.dao.IncommingPackage;

/**
 * Created by tarcisio on 11/10/16.
 */

public class PackageToIncommingPackageDataMapper implements DataMapper<Package, IncommingPackage> {

    public final static PackageToIncommingPackageDataMapper INSTANCE = new PackageToIncommingPackageDataMapper();

    @Override
    public IncommingPackage map(final Package source) {
        return new IncommingPackage() {
            @Override
            public String getDescription() {
                return source.description;
            }

            @Override
            public String getSender() {
                return source.sender;
            }

            @Override
            public String getTransporter() {
                return source.transporter;
            }

            @Override
            public long getSenddate() {
                return source.senddate;
            }

            @Override
            public long getInitialEta() {
                return source.initialEta;
            }

            @Override
            public long getCurrentEta() {
                return source.currentEta;
            }

            @Override
            public Location getCurrentLocation() {
                return null;
            }

            @Override
            public long getLastAtualizationTime() {
                return source.lastAtualizationTime;
            }
        };
    }
}
