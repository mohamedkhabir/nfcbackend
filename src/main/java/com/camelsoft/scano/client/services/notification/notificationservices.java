package com.camelsoft.scano.client.services.notification;
import com.camelsoft.scano.client.Enum.Action;
import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.Repository.Notifications.notificationrepository;
import com.camelsoft.scano.client.Requests.notification.Note;
import com.camelsoft.scano.client.Response.NotificationResponse;
import com.camelsoft.scano.client.models.Auth.UserDevice;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.notification.notificationmodel;
import com.camelsoft.scano.client.services.Auth.UserDeviceService;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.tools.exception.NotFoundException;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class notificationservices {
    @Autowired
    private notificationrepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private FireBaseFCMService pushNotificationService;

    public notificationmodel save(notificationmodel model) {
        try {
            return this.repository.save(model);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }

    public boolean existbyid(Long id) {
        try {
            return this.repository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("not found data"));
        }
    }

    public void deletebyid(Long id) {
        try {
            this.repository.deleteById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("not found data"));
        }
    }

    public notificationmodel update(notificationmodel model) {
        try {
            return this.repository.save(model);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }

    public notificationmodel findbyid(Long id) {
        try {
            return this.repository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }

    public NotificationResponse allnotificationbyuser(int page, int size, users user) {
        try {
            List<notificationmodel> resultlist = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<notificationmodel> pageTuts = this.repository.findAllByReciverOrderByTimestmpDesc(paging, user);
            resultlist = pageTuts.getContent();
            NotificationResponse response = new NotificationResponse(
                    resultlist,
                    pageTuts.getNumber(),
                    pageTuts.getTotalElements(),
                    pageTuts.getTotalPages()
            );
            this.changenotificationstate(resultlist);
            return response;
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    private void changenotificationstate(List<notificationmodel> notification) {
        for (notificationmodel nf : notification) {
            nf.setStatus(states.DELIVERED);
            this.update(nf);
        }

    }

    public NotificationResponse allnotificationbyuserbystate(int page, int size, users user, states status) {
        try {
            List<notificationmodel> resultlist = new ArrayList<notificationmodel>();
            Pageable paging = PageRequest.of(page, size);
            Page<notificationmodel> pageTuts = this.repository.findAllByReciverAndStatus(paging, user, status);
            resultlist = pageTuts.getContent();
            NotificationResponse response = new NotificationResponse(
                    resultlist,
                    pageTuts.getNumber(),
                    pageTuts.getTotalElements(),
                    pageTuts.getTotalPages()
            );
            this.changenotificationstate(resultlist);
            return response;
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    private String converMessageenum(Action action) {
        switch (action) {
            case NEW_MESSAGE:
                return "You have new message received";
            case NEW_USER:
                return "You have new user";
            default:
                return "You have new notification";
        }
    }

    public void sendnotification(notificationmodel notificationuser, notificationmodel notificationadmin) throws InterruptedException, FirebaseMessagingException {
        Action action = Action.IDLE;
        if (notificationuser != null) {
            notificationmodel resultadminnotificationuser = this.save(notificationuser);
            action = resultadminnotificationuser.getAction();
            Thread.sleep(1000);
            Note note = new Note();
            note.setSubject(action.name());
            note.setContent(this.converMessageenum(action));
            note.setData(notificationuser.toMap());
            if (!this.userDeviceService.findbyuserdevice(notificationuser.getReciver()).isEmpty()) {
                List<UserDevice> devices = this.userDeviceService.findbyuserdevice(notificationuser.getReciver());
                for (UserDevice device : devices) {
                    if (device != null && device.getTokendevice() != null && !"".equals(device.getTokendevice())) {
                        if (this.pushNotificationService.isValidFCMToken(device.getTokendevice())) {

                            this.pushNotificationService.sendNotification(note, device.getTokendevice());

                            Thread.sleep(1000);

                        } else {
                            this.userDeviceService.deletebyid(device.getId());

                        }

                    }
                }


            }

        }
        if (notificationadmin != null) {
            List<users> admins = this.userService.FindAllAdmin();
            for (users admin : admins) {
                notificationadmin.setReciver(admin);
                notificationmodel resultadminnotification = this.save(notificationadmin);
                Thread.sleep(1000);
            }
        }
    }

    public void sendalert (notificationmodel notificationuser, String subject,String content) throws InterruptedException, FirebaseMessagingException {
        if (notificationuser != null) {
            Thread.sleep(1000);
            Note note = new Note();
            note.setSubject(subject);
            note.setContent(content);
            note.setData(notificationuser.toMap());
            if (!this.userDeviceService.findbyuserdevice(notificationuser.getReciver()).isEmpty()) {
                List<UserDevice> devices = this.userDeviceService.findbyuserdevice(notificationuser.getReciver());
                for (UserDevice device : devices) {
                    if (device != null && device.getTokendevice() != null && !"".equals(device.getTokendevice())) {
                        if (this.pushNotificationService.isValidFCMToken(device.getTokendevice())) {

                            this.pushNotificationService.sendNotification(note, device.getTokendevice());

                            Thread.sleep(1000);

                        } else {
                            this.userDeviceService.deletebyid(device.getId());

                        }

                    }
                }


            }

        }



    }
}
