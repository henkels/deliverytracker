package br.com.deliverytracker.dao;

import java.util.List;

/**
 * Created by tarcisio on 17/07/16.
 */
public interface DataLoader {
    List<IncommingPackage> loadIncommingPackages(int count);

    void bind(DataBinder<IncommingPackage> dataBinder, int count);

    void newSender(Sender sender);
}
