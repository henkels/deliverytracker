package br.com.deliverytracker.receivingmanager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import br.com.deliverytracker.commom.XMPPMessage;
import br.com.deliverytracker.commom.server.LinkUserToDevice;
import br.com.deliverytracker.receivingmanager.pushnotification.MessageSender;

/**
 * Created by Tarcisio.henkels on 23/09/2016.
 */

public class DeviceManager {

    private DeviceManager(){}

    public final static DeviceManager INSTANCE = new DeviceManager();

    public void linkDevice() {
        String deviceID = FirebaseInstanceId.getInstance().getToken();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userMail = null;
        if (user != null) {
            userMail = user.getEmail();
        }

        if (deviceID != null && userMail != null) {
            XMPPMessage msg = new XMPPMessage();
            msg.data = new LinkUserToDevice(userMail, deviceID);
            MessageSender.SendMessage(msg);
        }
    }

}
