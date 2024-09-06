package com.camelsoft.scano.client.Controller;
import com.camelsoft.scano.client.Enum.Action;
import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.Interfaces.CusomUserResponse;
import com.camelsoft.scano.client.Repository.Auth.UserRepository;
import com.camelsoft.scano.client.Requests.Auth.*;
import com.camelsoft.scano.client.Response.CustomSignupResponse;
import com.camelsoft.scano.client.Response.JwtResponse;
import com.camelsoft.scano.client.models.Auth.*;
import com.camelsoft.scano.client.models.Auth.Tools.card_packages;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.client.models.UserTools.nfc_card;
import com.camelsoft.scano.client.models.notification.notificationmodel;
import com.camelsoft.scano.client.services.Auth.*;
import com.camelsoft.scano.client.services.Auth.Tools.card_package_service;
import com.camelsoft.scano.client.services.File.FileServices;
import com.camelsoft.scano.client.services.File.FilesStorageServiceImpl;
import com.camelsoft.scano.client.services.notification.notificationservices;
import com.camelsoft.scano.client.services.projecttools.Encryption;
import com.camelsoft.scano.client.services.projecttools.nfc_cardService;
import com.camelsoft.scano.client.services.projecttools.requestCompanyServices;
import com.camelsoft.scano.tools.Enum.Auth.paymentstate;
import com.camelsoft.scano.tools.exception.NotFoundException;
import com.camelsoft.scano.tools.exception.TokenRefreshException;
import com.camelsoft.scano.tools.util.TokenUtil;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.camelsoft.scano.client.Requests.Auth.signuprequest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.concurrent.Executors;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final Log logger = LogFactory.getLog(UserService.class);

    @Autowired
    private notificationservices _notificationservices;

    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Encryption encryption;
    @Autowired
    private requestCompanyServices requestcompanyServices;
    @Autowired
    private companyService companyservice;
    @Autowired
    private FilesStorageServiceImpl filesStorageService;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private card_package_service Card_package_service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordResetTokenServices resetTokenServices;
    @Autowired
    private nfc_cardService nfcCardService;
    @Autowired
    private MailSenderServices mailSenderServices;
    @Autowired
    private UserDeviceService deviceService;

    private static final List<String> image_accepte_type = Arrays.asList("JPEG", "jpeg", "svg", "png", "SVG", "PNG", "JPG", "jpg");
    @PostMapping(value = {"/signin"})
    public ResponseEntity<JwtResponse> signIn(@RequestBody(required = false) SignInRequest signInRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userService.loadUserByUsername(signInRequest.getUsername());

        users user = userRepository.findByUsername(userDetails.getUsername());
        List<UserDevice> userDeviceList = this.deviceService.findbyuser(user);
        if (userDeviceList != null && !userDeviceList.isEmpty())
            this.deviceService.deleteexpirationtoken(userDeviceList, userDetails);
        if (user == null)
            return new ResponseEntity("this user not found", HttpStatus.NOT_FOUND);
        String token = tokenUtil.generateToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername()).getToken();
        String roles;
        try {
            roles = userDetails.getAuthorities().stream().findFirst().get().toString();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("user messing data"));
        }

        UserDevice device = new UserDevice(user, signInRequest.getDeviceType(), signInRequest.getDeviceId(), signInRequest.getIp(), token, signInRequest.getTokendevice());
        UserDevice result_device = this.deviceService.save(device);
        JwtResponse response = new JwtResponse(token, refreshToken, roles, result_device.getDeviceId(), result_device.getDeviceType(), result_device.getIp());


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"/company_code_check"})
    public ResponseEntity<Boolean> company_code_check (@RequestParam String companycode) {

        Boolean res = this.companyservice.existByPromocode(companycode);


            return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        UserDevice device = this.deviceService.findbytoken(request.getToken());

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {

                    String token = tokenUtil.generateToken(userService.loadUserByUsername(user.getUsername()));
                    String roles = userService.loadUserByUsername(user.getUsername()).getAuthorities().stream().findFirst().get().toString();
                    if (device == null) {
                        UserDevice devices = new UserDevice(user, request.getDeviceType(), request.getDeviceId(), request.getIp(), token, request.getTokendevice());
                        this.deviceService.save(devices);
                    } else {
                        device.setToken(token);
                        this.deviceService.update(device);
                    }

                    return ResponseEntity.ok(new JwtResponse(token, requestRefreshToken, roles, device.getDeviceId(), device.getDeviceType(), device.getIp()));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping(value = {"/signup_user"})
    public ResponseEntity<CustomSignupResponse> signup_user(@ModelAttribute signuprequest request, @RequestParam(value = "file",required = false) MultipartFile file) throws IOException, InterruptedException, MessagingException {
        users user = userRepository.findByEmail(request.getEmail());
        company companie =null;
        users usersList = this.userService.findTop();
        Long lastuserid = usersList.getId() + 1;
        if (request.getPromocode()!=null)
        {
            if (!this.companyservice.existByPromocode(request.getPromocode()))
                return new ResponseEntity("Wrong promocode"+request.getPromocode() , HttpStatus.BAD_REQUEST);
            companie = this.companyservice.findByPromocode(request.getPromocode());
        }
        File_model resource_media = null;
        String username = userService.GenerateUserName(request.getFirstname()+request.getLastname(), lastuserid);

        if (request.getEmail() == null) {
            return new ResponseEntity("data missing", HttpStatus.CONFLICT);
        }
        if (file != null) {
            String extention = file.getContentType().substring(file.getContentType().indexOf("/") + 1).toLowerCase(Locale.ROOT);
            if (extention.contains("+xml") && extention.contains("svg"))
                extention = "svg";
            if (!image_accepte_type.contains(extention))
                return new ResponseEntity("this type is not acceptable : " + extention, HttpStatus.BAD_REQUEST);
             resource_media = filesStorageService.save_file(file, "profile", username, extention);
        }
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            user = new users(
                    username,
                    request.getEmail(),
                    request.getPassword(),
                    request.getLastname(),
                    request.getFirstname(),
                    request.getCardemail()
            );

            users result = userService.saveUser(user);
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(result.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userService.loadUserByUsername(result.getUsername());
            String token = tokenUtil.generateToken(userDetails);
            String refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername()).getToken();

            if (resource_media != null) {
                result.setImage(resource_media);
                result = this.userService.UpdateUser(result);
            }

            if (companie != null) {
                companie.addSubscribers(result);

                this.companyservice.UpdateCompany(companie);
                card_packages cd_package = this.Card_package_service.findTop();

                nfc_card card = this.nfcCardService.save(new nfc_card(companie.getAmount(), "paied by company", "", "", paymentstate.PAID_BY_COMPANY, states.WAITING));
                card.setUser(user);

                cd_package.addCard(card);
                user.getCard().add(card);

                this.userService.UpdateUser(user);

                this.Card_package_service.Updatesocialmedia(cd_package);
            }
            CustomSignupResponse response = new CustomSignupResponse(result,token,refreshToken);


            Runnable notificationTask = () -> {

                notificationmodel notificationuser = new notificationmodel(null, null, Action.NEW_USER);

                try {
                    this._notificationservices.sendnotification(null,notificationuser);
                } catch (FirebaseMessagingException | InterruptedException e) {
                    e.printStackTrace();
                }
            };

            // Submit the notification task to a new thread
            Executors.newSingleThreadExecutor().submit(notificationTask);


            return new ResponseEntity<>(response, HttpStatus.OK);

        }
    }

    @PostMapping(value = {"/signup_user_web"})
    public ResponseEntity<users> signup_user_web (@ModelAttribute signuprequest request) throws IOException, InterruptedException, MessagingException {
        users user = userRepository.findByEmail(request.getEmail());
        company companie =null;
        users usersList = this.userService.findTop();
        Long lastuserid = usersList.getId() + 1;
        if (request.getPromocode()!=null)
        {
            if (!this.companyservice.existByPromocode(request.getPromocode()))
                return new ResponseEntity("Wrong promocode"+request.getPromocode() , HttpStatus.BAD_REQUEST);
            companie = this.companyservice.findByPromocode(request.getPromocode());
        }
        String username = userService.GenerateUserName(request.getFirstname()+request.getLastname(), lastuserid);

        if (request.getEmail() == null) {
            return new ResponseEntity("data missing", HttpStatus.CONFLICT);
        }

        if (user != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            user = new users(
                    username,
                    request.getEmail(),
                    request.getPassword(),
                    request.getLastname(),
                    request.getFirstname(),
                    request.getAddress(),
                    request.getBuilding(),
                    request.getUnitnumberaddress(),
                    request.getCity(),
                    request.getCardemail());

            users result = userService.saveUser(user);


            if (companie != null) {
                companie.addSubscribers(result);

                this.companyservice.UpdateCompany(companie);
                card_packages cd_package = this.Card_package_service.findTop();

                nfc_card card = this.nfcCardService.save(new nfc_card(companie.getAmount(), "paied by company", "", "", paymentstate.PAID_BY_COMPANY, states.WAITING));
                card.setUser(user);

                cd_package.addCard(card);
                user.getCard().add(card);

                this.userService.UpdateUser(user);

                this.Card_package_service.Updatesocialmedia(cd_package);
            }

            Runnable notificationTask = () -> {

                notificationmodel notificationuser = new notificationmodel(null, null, Action.NEW_USER);

                try {
                    this._notificationservices.sendnotification(null,notificationuser);
                } catch (FirebaseMessagingException | InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Executors.newSingleThreadExecutor().submit(notificationTask);


            return new ResponseEntity<>(result, HttpStatus.OK);

        }
    }

    @PostMapping(value = {"/reset_password_first_step"})
    public ResponseEntity reset_password_first_step(@RequestParam("email") String email) throws IOException {
        if (!this.userService.existbyemail(email))
            return new ResponseEntity("user not found", HttpStatus.NOT_FOUND);
        users user = userService.findbyemail(email);
        String token = PasswordResetTokenServices.generateRandomResetCode(4);
        PasswordResetToken resetToken = this.resetTokenServices.findbyuser(user);
        if (resetToken != null) {
            this.resetTokenServices.remove_code(resetToken.getUser());
        }
        resetToken = this.resetTokenServices.createPasswordResetTokenForUser(user, token);
        this.mailSenderServices.sendEmailResetPassword(resetToken.getToken(), user, resetToken.getExpiryDate());
        //sendEmailWithAttachment();
        System.out.println("Done");
        return new ResponseEntity("code send it to email , the code wille be expired in : " + resetToken.getExpiryDate(), HttpStatus.OK);
    }

    @PostMapping(value = {"/validate_reset_code_second_step"})
    public ResponseEntity<String> validate_reset_code_second_step(@RequestParam("email") String email, @RequestParam("reset_code") String code) throws IOException {
        if (!this.userService.existbyemail(email))
            return new ResponseEntity("user not found", HttpStatus.NOT_FOUND);
        users user = userService.findbyemail(email);
        PasswordResetToken resetToken = this.resetTokenServices.findbyuser(user);
        if (resetToken == null) {
            return new ResponseEntity("this code is not found", HttpStatus.NOT_FOUND);

        }
        String result = this.resetTokenServices.validatePasswordResetToken(user, code);
        if (result == null)
            return new ResponseEntity<>("code correct", HttpStatus.OK);

        if (result.equals("expired"))
            return new ResponseEntity("this code is expired", HttpStatus.NOT_ACCEPTABLE);
        if (result.equals("invalidToken"))
            return new ResponseEntity("this code is not valid", HttpStatus.NOT_ACCEPTABLE);


        return new ResponseEntity("this code is wrong", HttpStatus.NOT_ACCEPTABLE);


    }

    @PostMapping(value = {"/change_password_final_step"})
    public ResponseEntity change_password_final_step(@RequestParam("email") String email, @RequestParam("reset_code") String code, @RequestParam("password") String new_password) throws IOException {
        if (!this.userService.existbyemail(email))
            return new ResponseEntity("user not found", HttpStatus.NOT_FOUND);
        users user = userService.findbyemail(email);
        PasswordResetToken resetToken = this.resetTokenServices.findbyuser(user);
        if (resetToken == null) {
            return new ResponseEntity("this code is not found", HttpStatus.NOT_FOUND);
        }
        String result = this.resetTokenServices.validatePasswordResetToken(user, code);
        if (result == null) {
            this.userService.updatepassword(user, new_password);
            this.resetTokenServices.remove_code(resetToken.getUser());
            return new ResponseEntity("password updated successfully !!", HttpStatus.OK);
        }
        if (result.equals("expired"))
            return new ResponseEntity("this code is expired", HttpStatus.NOT_ACCEPTABLE);
        if (result.equals("invalidToken"))
            return new ResponseEntity("this code is not valid", HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity("this code is wrong", HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(value = {"/get_user_by_card_uid/{uid}"})
    public ResponseEntity <CusomUserResponse> get_user_by_card_uid (@PathVariable String uid) throws Exception {

        logger.info(uid);
        if (!this.nfcCardService.existbyUid(uid))
            return new ResponseEntity("Uid already exist cant add 2 users to one card : " + uid, HttpStatus.CONFLICT);

        CusomUserResponse result = this.userService.findByUser_UId(uid);
        if(result.getActive()!=null && !result.getActive()){
            return new ResponseEntity("this user is suspend", HttpStatus.NOT_ACCEPTABLE);
        }
        logger.info("Auth Controller  done !");
        this.nfcCardService.runAsyncTask(uid);
        return new ResponseEntity(result,HttpStatus.OK);
    }


    @GetMapping(value = {"/get_QR_code"})
    public ResponseEntity <CusomUserResponse> get_QR_code (@RequestParam String url) throws Exception {

        File_model result = this.filesStorageService.saveQRCodeImageFromUrl(url,"test","testing.","png");
        logger.info("Auth Controller  done !");
        return new ResponseEntity(result,HttpStatus.OK);
    }

//    @PostMapping(value = {"/encrypt"})
//    public ResponseEntity <String> encrypt (@RequestBody String uid) throws Exception {
//
//        String s = encryption.encryptUrlSafe(uid);
//        logger.info(uid);
//        logger.info(s);
//
//
//        this.nfcCardService.runAsyncTask(s);
//        return new ResponseEntity(s,HttpStatus.OK);
//    }

    @GetMapping(value = {"/get_user_by_card_uidd"})
    public ResponseEntity <CusomUserResponse> get_user_by_card_uidd (@RequestHeader String uid) throws Exception {


//        String s = encryption.decrypt(uid);
        CusomUserResponse result = this.userService.findByUser_UId(uid);
        logger.info("Auth Controller  done !");

        this.nfcCardService.runAsyncTask(uid);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @PostMapping(value = {"/company_request"})
    public ResponseEntity<company_request> company_request(@ModelAttribute submitcompanyRequest request) {
        company_request companies = new company_request(
                request.getLastname(),
                request.getFirstname(),
                request.getPhonenumber(),
                request.getMessage());


        notificationmodel notificationuser = new notificationmodel(null, null, Action.NEW_COMPANY_REQUEST);

        try {
            this._notificationservices.sendnotification(null,notificationuser);
        } catch (FirebaseMessagingException | InterruptedException e) {
            e.printStackTrace();
        }
        company_request result = this.requestcompanyServices.Update(companies);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = {"/inc_scans_by_card_uid/{uid}"})
    public ResponseEntity <nfc_card> inc_scans_by_card_uid (@PathVariable String uid)
    {
        if (!this.nfcCardService.existbyUid(uid))
            return new ResponseEntity("this uid is not found "+uid, HttpStatus.NOT_FOUND);

        nfc_card result = this.nfcCardService.findByUid(uid);
        result.IncScans();
        result = this.nfcCardService.save(result);

        return new ResponseEntity(result,HttpStatus.OK);

    }

}