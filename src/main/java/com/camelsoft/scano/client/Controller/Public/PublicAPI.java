package com.camelsoft.scano.client.Controller.Public;


import com.camelsoft.scano.client.Controller.UsersController;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.services.Auth.MailSenderServices;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.client.services.Country.CountriesServices;
import com.camelsoft.scano.client.services.projecttools.Encryption;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/public")
public class PublicAPI {



    @Autowired
    private UserService userService;




    @Autowired
    private MailSenderServices mailSenderServices;



//    @PostMapping(value = {"/change_password_final_step"})
//    public ResponseEntity change_password_final_step(@RequestParam("email") String email, @RequestParam("password") String new_password) throws IOException
//    {
//        if (!this.userService.existbyemail(email))
//            return new ResponseEntity("user not found", HttpStatus.NOT_FOUND);
//        users user = userService.findbyemail(email);
//            this.userService.updatepassword(user, new_password);
//
//sekta jemla
    
//            return new ResponseEntity<>("done", HttpStatus.OK);
//        }


    @GetMapping(value = {"/all"})
    public ResponseEntity<List<users>> all() throws IOException {
        List<users> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = {"/contact_us"})
    public ResponseEntity contact_us(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("phonenumber") String phonenumber,@RequestParam("content") String content,@RequestParam("reason") String reason) throws IOException {
        this.mailSenderServices.sendContactUsEmail(name,email,phonenumber,content,reason);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = {"/findemail/{id}"})
    public ResponseEntity<users> findemail(@PathVariable String id) throws IOException {
        users user = userService.findbyemail(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = {"/count"})
    public ResponseEntity<Integer> count() throws IOException {
        List<users> users = userService.findAll();
        return new ResponseEntity<>(users.size(), HttpStatus.OK);
    }

}
