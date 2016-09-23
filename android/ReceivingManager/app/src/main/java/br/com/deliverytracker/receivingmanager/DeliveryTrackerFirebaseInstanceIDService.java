package br.com.deliverytracker.receivingmanager;

import android.util.Log;

import com.google.android.gms.auth.api.credentials.PasswordSpecification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

import br.com.deliverytracker.commom.XMPPMessage;
import br.com.deliverytracker.commom.server.LinkUserToDevice;
import br.com.deliverytracker.receivingmanager.pushnotification.MessageSender;

/**
 * Created by tarcisio on 28/07/16.
 */
public class DeliveryTrackerFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private DeliveryTrackerFirebaseInstanceIDService() {
        super();
        onTokenRefresh();
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        DeviceManager.INSTANCE.linkDevice();
    }
    // [END refresh_token]

}
