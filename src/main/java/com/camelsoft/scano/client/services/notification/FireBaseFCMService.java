package com.camelsoft.scano.client.services.notification;


import com.camelsoft.scano.client.Requests.notification.Note;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FireBaseFCMService {

    private final FirebaseMessaging firebaseMessaging;

    public FireBaseFCMService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }
    public String sendNotification(Note note, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();
        return firebaseMessaging.send(message);
    }
    public Boolean isValidFCMToken(String fcmToken) {
        Message message = Message.builder().setToken(fcmToken).build();
        try {
            firebaseMessaging.send(message,true);
            return true;
        } catch (FirebaseMessagingException fme) {
            return false;
        }
    }
}
