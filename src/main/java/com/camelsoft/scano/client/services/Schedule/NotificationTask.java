package com.camelsoft.scano.client.services.Schedule;

import com.camelsoft.scano.client.Enum.Action;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.notification.notificationmodel;
import com.camelsoft.scano.client.services.notification.notificationservices;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationTask implements Runnable {

    private notificationservices _notificationservices;
    private users param1;
    private users param2;
    private Action action;

    public NotificationTask(notificationservices notificationservices, users param1, users param2, Action action) {
        this._notificationservices = notificationservices;
        this.param1 = param1;
        this.param2 = param2;
        this.action = action;
    }

    @Override
    public void run() {
        notificationmodel notificationuser = new notificationmodel(param1, param2, action);

        try {
            this._notificationservices.sendnotification(null, notificationuser);
        } catch (FirebaseMessagingException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}