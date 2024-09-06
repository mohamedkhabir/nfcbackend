package com.camelsoft.scano.client.Controller.notification;


import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.Response.NotificationResponse;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.notification.notificationmodel;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.client.services.notification.notificationservices;
import com.camelsoft.scano.tools.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/notification")
public class notificationcontroller extends BaseController {
    @Autowired
    private notificationservices services;

    @Autowired
    private UserService userService;
    @GetMapping(value = {"/all_my_notification"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<NotificationResponse> all_my_notification(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) throws IOException {
        users user = this.userService.findByUserName(getCurrentUser().getUsername());
        NotificationResponse result = this.services.allnotificationbyuser(page, size,user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = {"/all_my_notification_state"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<NotificationResponse> all_my_notification_waiting(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,@RequestParam states state) throws IOException {
        users user = this.userService.findByUserName(getCurrentUser().getUsername());
        NotificationResponse result = this.services.allnotificationbyuserbystate(page, size,user,state);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping(value = {"/read_my_waiting_notification"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<notificationmodel>> read_my_waiting_notification (@RequestParam Long[] notificationid) throws IOException {

        List<notificationmodel> result = new ArrayList<>();
        for (Long id: notificationid) {

            if(this.services.existbyid(id)){

                notificationmodel not = this.services.findbyid(id);

                not.setStatus(states.READ);

                not = this.services.save(not);

                result.add(not);

            }else {
                return new ResponseEntity("invalid id "  + id, HttpStatus.BAD_REQUEST);

            }
        }


        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
