package com.camelsoft.scano.client.Controller.notification;

import com.camelsoft.scano.client.Enum.Action;
import com.camelsoft.scano.client.Requests.notification.notificationrequest;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.notification.notificationmodel;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.client.services.notification.FireBaseFCMService;
import com.camelsoft.scano.client.services.notification.notificationservices;
import com.camelsoft.scano.tools.util.BaseController;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/notification")
public class PushNotificationController extends BaseController {
    @Autowired
    private FireBaseFCMService pushNotificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private notificationservices _notificationservices;

    @PostMapping("/send-notification")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPPLIER') or hasRole('DRIVER')")
    public String sendNotification(@RequestBody notificationrequest request) throws FirebaseMessagingException {

        if (!this.pushNotificationService.isValidFCMToken(request.getToken()))
            return "The registration token is not a valid FCM registration token.";

        return pushNotificationService.sendNotification(request.getNote(), request.getToken());
    }

    @PostMapping("/send-notification-test")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPPLIER') or hasRole('DRIVER')")
    public void sendnotificationtest(@RequestBody notificationrequest request) throws FirebaseMessagingException {


        users user = this.userService.findByUserName(getCurrentUser().getUsername());

        notificationmodel notificationuser = new notificationmodel(user, user, Action.NEW_USER);
        try {
            this._notificationservices.sendnotification(notificationuser, null);
        } catch (FirebaseMessagingException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
