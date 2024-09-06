package com.camelsoft.scano.client.Controller;

import com.camelsoft.scano.client.Enum.Action;
import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.Enum.step;
import com.camelsoft.scano.client.Repository.Auth.RefreshTokenRepository;
import com.camelsoft.scano.client.Requests.Auth.LogOutRequest;
import com.camelsoft.scano.client.Requests.Auth.Tools.cardRequest;
import com.camelsoft.scano.client.Requests.Auth.Tools.cardRequestList;
import com.camelsoft.scano.client.Requests.Auth.Tools.crad_package_request;
import com.camelsoft.scano.client.Requests.Auth.user_update_request;
import com.camelsoft.scano.client.Response.ApiResponse;
import com.camelsoft.scano.client.Response.OnUserLogoutSuccessEvent;
import com.camelsoft.scano.client.models.Auth.Tools.CardRequests;
import com.camelsoft.scano.client.models.Auth.Tools.card_packages;
import com.camelsoft.scano.client.models.Auth.UserDevice;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.client.models.UserTools.nfc_card;
import com.camelsoft.scano.client.models.notification.notificationmodel;
import com.camelsoft.scano.client.services.Auth.Tools.card_package_service;
import com.camelsoft.scano.client.services.Auth.UserDeviceService;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.client.services.File.FileServices;
import com.camelsoft.scano.client.services.File.FilesStorageServiceImpl;
import com.camelsoft.scano.client.services.notification.notificationservices;
import com.camelsoft.scano.client.services.projecttools.CardRequestsServices;
import com.camelsoft.scano.client.services.projecttools.Encryption;
import com.camelsoft.scano.client.services.projecttools.nfc_cardService;
import com.camelsoft.scano.tools.Enum.Auth.paymentstate;
import com.camelsoft.scano.tools.exception.UserLogoutException;
import com.camelsoft.scano.tools.util.BaseController;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1")
public class UsersController extends BaseController {
    private final Log logger = LogFactory.getLog(UsersController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private CardRequestsServices cardRequestsServices;
    @Autowired
    private Encryption encryption;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private nfc_cardService nfcCardService;
    @Autowired
    private card_package_service Card_package_service;
    @Autowired
    private FileServices fileServices;
    @Autowired
    private notificationservices _notificationservices;
    @Autowired
    private FilesStorageServiceImpl filesStorageService;
    private static final List<String> image_accepte_type = Arrays.asList("JPEG", "jpeg", "svg", "png", "SVG", "PNG", "JPG", "jpg");
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PatchMapping(value = {"/suspend_user/{user_id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<users> suspend_user(@PathVariable Long user_id) throws IOException {
        if (!this.userService.existbyid(user_id))
            return new ResponseEntity("user not found", HttpStatus.NOT_FOUND);
        users user = userService.findById(user_id);
        user.setActive(!user.getActive());
        user = this.userService.UpdateUser(user);
        if (user.getActive()) {
            Runnable notificationTask = () -> {
                notificationmodel notificationuser;
                notificationuser = new notificationmodel(null, null, Action.ACCOUNT_ACTIVATED);
                try {
                    this._notificationservices.sendnotification(notificationuser, null);
                } catch (FirebaseMessagingException | InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Executors.newSingleThreadExecutor().submit(notificationTask);
        } else {
            Runnable notificationTask = () -> {
                notificationmodel notificationuser;
                notificationuser = new notificationmodel(null, null, Action.ACCOUNT_SUSPENDED);
                try {
                    this._notificationservices.sendnotification(notificationuser, null);
                } catch (FirebaseMessagingException | InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Executors.newSingleThreadExecutor().submit(notificationTask);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping(value = {"/delete"})
    public ResponseEntity<users> delete() throws IOException {
        users user = userService.findByUserName(getCurrentUser().getUsername());
        user.setActive(false);
        user = this.userService.UpdateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = {"/current_user"})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<users> GetCurrentUser() throws IOException {
        users users = userService.findByUserName(getCurrentUser().getUsername());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = {"/add_card"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> add_card(@RequestParam String card_Uid) throws IOException, WriterException {
        users user = userService.findByUserName(getCurrentUser().getUsername());
        if (this.nfcCardService.existbyUid(card_Uid))
            return new ResponseEntity("Uid already exist cant add 2 users to one card : " + card_Uid, HttpStatus.CONFLICT);

        if (card_Uid == "null" )
            return new ResponseEntity("null value not accepted : " + card_Uid, HttpStatus.CONFLICT);
        if (user.getCardRequests().isEmpty())
            return new ResponseEntity("no card requested in first place: " + user.getId(), HttpStatus.CONFLICT);


        nfc_card card = new nfc_card();

        logger.info(card.getId());
        card.setState(states.DELIVERED);
        card.setUser(user);
        card.setUid(card_Uid);
        user.getCard().add(card);
        user.setSteps(step.CARD_LINKED);
        card.setWalletpaiementstate(paymentstate.UNPAID);

        card.setRecievedate(new Date());
        File_model result = this.filesStorageService.saveQRCodeImageFromUrl("https://profile.scano-online.com/profile/" + card_Uid, user.getUsername(), "card_Uid.", "png");
        card.setQrImage(result);
        this.userService.UpdateUser(user);
        Runnable notificationTask = () -> {
            notificationmodel notificationuser = new notificationmodel(null, null, Action.CARD_RECEIVED);
            try {
                this._notificationservices.sendnotification(null, notificationuser);
            } catch (FirebaseMessagingException | InterruptedException e) {
                e.printStackTrace();
            }
        };
        Executors.newSingleThreadExecutor().submit(notificationTask);
        return new ResponseEntity<>(card_Uid, HttpStatus.OK);
    }


    @PostMapping(value = {"/pay_card"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<nfc_card>> pay_card() throws IOException, WriterException {
        users user = userService.findByUserName(getCurrentUser().getUsername());

        if (user.getCardRequests().isEmpty())
            return new ResponseEntity("no card requested in first place: " + user.getId(), HttpStatus.CONFLICT);

        List<nfc_card> card = this.nfcCardService.findAllByUser(user);

        for (nfc_card nfcCard : card) {
            nfcCard.setWalletpaiementstate(paymentstate.PAID);
            this.nfcCardService.save(nfcCard);
        }
        List<nfc_card> card_Uid = this.nfcCardService.findAllByUser(user);
        return new ResponseEntity<>(card_Uid, HttpStatus.OK);
    }

    @PostMapping(value = {"/add_new_card"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> add_new_card(@RequestParam String card_Uid) throws IOException, WriterException {
        users user = userService.findByUserName(getCurrentUser().getUsername());
        if (card_Uid == "null")
            return new ResponseEntity("invalid card id : " + card_Uid, HttpStatus.CONFLICT);
        if (this.nfcCardService.existbyUid(card_Uid))
            return new ResponseEntity("Uid already exist cant add 2 users to one card : " + card_Uid, HttpStatus.CONFLICT);



        nfc_card card = new nfc_card();

        logger.info(card.getId());
        card.setState(states.DELIVERED);
        card.setWalletpaiementstate(paymentstate.UNPAID);
        card.setUser(user);
        card.setUid(card_Uid);
        user.getCard().add(card);
        user.setSteps(step.CARD_LINKED);
        card.setRecievedate(new Date());
        File_model result = this.filesStorageService.saveQRCodeImageFromUrl("https://profile.scano-online.com/profile/" + card_Uid, user.getUsername(), "card_Uid.", "png");
        card.setQrImage(result);
        this.userService.UpdateUser(user);
        Runnable notificationTask = () -> {
            notificationmodel notificationuser = new notificationmodel(null, null, Action.CARD_RECEIVED);
            try {
                this._notificationservices.sendnotification(null, notificationuser);
            } catch (FirebaseMessagingException | InterruptedException e) {
                e.printStackTrace();
            }
        };
        Executors.newSingleThreadExecutor().submit(notificationTask);
        return new ResponseEntity<>(card_Uid, HttpStatus.OK);
    }


    @PostMapping(value = {"/request_card"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<CardRequests>> request_card(@RequestBody cardRequestList request) throws IOException {

        users user = userService.findByUserName(getCurrentUser().getUsername());
        //    if (this.nfcCardService.existByUser_Id(user.getId()))
        //          return new ResponseEntity("This user already has a card request !", HttpStatus.CONFLICT);
//        card_packages cd_package = this.Card_package_service.findTop();
        List<CardRequests> cardRequestList = new ArrayList<>();
        for (cardRequest cardRequeste : request.getCardRequests()) {
            CardRequests card = this.cardRequestsServices.save(new CardRequests(cardRequeste.getAmount(), cardRequeste.getStripeid(), cardRequeste.getColor(), cardRequeste.getStyle(), cardRequeste.getWalletpaiementstate(), states.WAITING));
            card.setUser(user);
            user.getCardRequests().add(card);
            cardRequestList.add(card);
        }

        this.userService.UpdateUser(user);
        Runnable notificationTask = () -> {
            notificationmodel notificationuser = new notificationmodel(null, user, Action.CARD_REQUESTED);
            try {
                this._notificationservices.sendnotification(null, notificationuser);
            } catch (FirebaseMessagingException | InterruptedException e) {
                e.printStackTrace();
            }
        };
        Executors.newSingleThreadExecutor().submit(notificationTask);
        return new ResponseEntity<>(cardRequestList, HttpStatus.OK);
    }

    @PatchMapping(value = {"/request_card_image/{package_id}"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CardRequests> request_card_image(@PathVariable Long package_id, @RequestParam(value = "file") MultipartFile file) throws IOException {

//        if (this.cardRequestsServices.existbyid(package_id))
//            return new ResponseEntity("This user already has a card request !", HttpStatus.CONFLICT);

        CardRequests card = this.cardRequestsServices.findById(package_id);
        String extention = file.getContentType().substring(file.getContentType().indexOf("/") + 1).toLowerCase(Locale.ROOT);
        if (extention.contains("+xml") && extention.contains("svg"))
            extention = "svg";
        if (!image_accepte_type.contains(extention))
            return new ResponseEntity("this type is not acceptable : " + extention, HttpStatus.BAD_REQUEST);

        File_model resource_media = this.filesStorageService.save_file(file, "cards", file.getOriginalFilename(), extention);
        card.setImage(resource_media);
        card = this.cardRequestsServices.save(card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }


    @DeleteMapping(value = {"/remove_card_for_test"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> remove_card_for_test(@RequestParam Long card_Uid) throws IOException {
        users user = userService.findByUserName(getCurrentUser().getUsername());
        if (!this.nfcCardService.existbyid(card_Uid))
            return new ResponseEntity("invalid " + card_Uid, HttpStatus.CONFLICT);
        nfc_card card = this.nfcCardService.findById(card_Uid);
        card.setUser(null);
        user.setUrl(null);

        user.getCard().remove(card);
        this.nfcCardService.delete(card);
        this.userService.UpdateUser(user);
        return new ResponseEntity<>("done", HttpStatus.OK);
    }

    @PutMapping(value = {"/logout"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ApiResponse> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        users currentUser = userService.findByUserName(getCurrentUser().getUsername());
        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        if (deviceId == null)
            return new ResponseEntity("device id not found", HttpStatus.NOT_ACCEPTABLE);
        if (this.userDeviceService.findbytoken(logOutRequest.getToken()) == null)
            return new ResponseEntity("user device not found", HttpStatus.NOT_ACCEPTABLE);
        UserDevice userDevice = this.userDeviceService.findbytoken(logOutRequest.getToken());
        if (userDevice != null && userDevice.getDeviceId().equals(deviceId)) {
            OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(currentUser.getEmail(), logOutRequest.getToken(), logOutRequest);
            applicationEventPublisher.publishEvent(logoutSuccessEvent);
            this.userDeviceService.deletebyid(userDevice.getId());
            return ResponseEntity.ok(new ApiResponse(true, "User has successfully logged out from the system!"));
        } else {
            throw new UserLogoutException(logOutRequest.getDeviceInfo().getDeviceId(), "Invalid device Id supplied. No matching device found for the given user ");
        }

    }

    @PatchMapping(value = {"/update_user"})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<users> update_user(@ModelAttribute user_update_request request, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        users user = this.userService.findByUserName(getCurrentUser().getUsername());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        File_model resource_media = user.getImage();
        if (file != null) {
            String extention = file.getContentType().substring(file.getContentType().indexOf("/") + 1).toLowerCase(Locale.ROOT);
            if (extention.contains("+xml") && extention.contains("svg"))
                extention = "svg";
            if (!image_accepte_type.contains(extention))
                return new ResponseEntity("this type is not acceptable : " + extention, HttpStatus.BAD_REQUEST);
            if (resource_media != null)
                this.filesStorageService.delete_file_by_path(resource_media.getUrl(), resource_media.getId());
            resource_media = this.filesStorageService.save_file(file, "profile", file.getOriginalFilename(), extention);
            user.setImage(resource_media);
        }
        if (request.getArea() != null) user.setArea(request.getArea());
        if (request.getFloor() != null) user.setFloor(request.getFloor());
        if (request.getAddresstype() != null) user.setAddresstype(request.getAddresstype());
        if (request.getBuilding() != null) user.setBuilding(request.getBuilding());
        if (request.getFax() != null) user.setFax(request.getFax());
        if (request.getNickname() != null) user.setNickname(request.getNickname());
        if (request.getStreet() != null) user.setStreet(request.getStreet());
        if (request.getUnitnumberaddress() != null) user.setUnitnumberaddress(request.getUnitnumberaddress());
        if (request.getLandline() != null) user.setLandline(request.getLandline());
        if (request.getCompany_name() != null) user.setCompany_name(request.getCompany_name());
        if (request.getWebsite() != null) user.setWebsite(request.getWebsite());
        if (request.getPosition() != null) user.setPosition(request.getPosition());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getUrl() != null && request.getUrl() != "null") {
            if (user.getUrl() ==null)
            user.setUrl(request.getUrl());
        }
        if (request.getLastname() != null) {
            user.setLastname(request.getLastname());
            if (request.getFirstname() == null)
                user.setName(request.getLastname() + " " + user.getFirstname());
        }
        if (request.getFirstname() != null) {

            user.setFirstname(request.getFirstname());
            if (request.getLastname() != null)
                user.setName(request.getLastname() + " " + request.getFirstname());
            else
                user.setName(user.getLastname() + " " + request.getFirstname());
        }
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getPhonenumber() != null) user.setPhonenumber(request.getPhonenumber());
        if (request.getCountry() != null) user.setCountry(request.getCountry());
        if (request.getCity() != null) user.setCity(request.getCity());
        if (request.getSteps() != null) user.setSteps(request.getSteps());
        if (request.getCardemail() != null) {user.setCardemail(request.getCardemail());}
        else{
            user.setCardemail(user.getEmail());
        }
        user.setImage(resource_media);
        users result = userService.UpdateUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping(value = {"/update_couverture"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<users> update_couverture (@RequestParam(value = "file") MultipartFile file) throws IOException {
        users user = this.userService.findByUserName(getCurrentUser().getUsername());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        File_model resource_media = user.getCouverture();
        if (file != null) {
            String extention = file.getContentType().substring(file.getContentType().indexOf("/") + 1).toLowerCase(Locale.ROOT);
            if (extention.contains("+xml") && extention.contains("svg"))
                extention = "svg";
            if (!image_accepte_type.contains(extention))
                return new ResponseEntity("this type is not acceptable : " + extention, HttpStatus.BAD_REQUEST);
            if (resource_media!=null)
                this.filesStorageService.delete_file_by_path(resource_media.getUrl(), resource_media.getId());
            resource_media = this.filesStorageService.save_file(file, "profile",file.getOriginalFilename(), extention);

        }

        user.setCouverture(resource_media);
        users result = userService.UpdateUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PatchMapping(value = {"/update_password"})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity update_password(@NotNull @RequestParam("password") String password, @NotNull @RequestParam("oldpassword") String oldpassword) throws IOException {
        users user = this.userService.findByUserName(getCurrentUser().getUsername());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(oldpassword, user.getPassword()))
            return new ResponseEntity("Wrong Password ", HttpStatus.BAD_REQUEST);
        user.setPassword(password);
        this.userService.update_password(user);
        return new ResponseEntity("password updated successfully", HttpStatus.OK);
    }

}
