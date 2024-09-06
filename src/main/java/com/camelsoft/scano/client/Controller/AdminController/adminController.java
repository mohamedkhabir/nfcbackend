package com.camelsoft.scano.client.Controller.AdminController;

import com.camelsoft.scano.client.Controller.UsersController;
import com.camelsoft.scano.client.Enum.requestState;
import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.Enum.step;
import com.camelsoft.scano.client.Repository.Criteria.CreteriaSearchRepository;
import com.camelsoft.scano.client.Repository.Criteria.PageConfig;
import com.camelsoft.scano.client.Requests.Auth.AdduserRequest;
import com.camelsoft.scano.client.Requests.Auth.Tools.crad_package_request;
import com.camelsoft.scano.client.Requests.Auth.signuprequest;
import com.camelsoft.scano.client.Requests.Auth.submitcompanyRequest;
import com.camelsoft.scano.client.Response.CustomSignupResponse;
import com.camelsoft.scano.client.Response.DynamicResponse;
import com.camelsoft.scano.client.models.Auth.Tools.card_packages;
import com.camelsoft.scano.client.models.Auth.Tools.note;
import com.camelsoft.scano.client.models.Auth.company;
import com.camelsoft.scano.client.models.Auth.company_request;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.client.models.UserTools.nfc_card;
import com.camelsoft.scano.client.services.Auth.Tools.card_package_service;
import com.camelsoft.scano.client.services.Auth.UserDeviceService;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.client.services.Auth.companyService;
import com.camelsoft.scano.client.services.File.FileServices;
import com.camelsoft.scano.client.services.File.FilesStorageServiceImpl;
import com.camelsoft.scano.client.services.projecttools.Encryption;
import com.camelsoft.scano.client.services.projecttools.nfc_cardService;
import com.camelsoft.scano.client.services.projecttools.noteService;
import com.camelsoft.scano.client.services.projecttools.requestCompanyServices;
import com.camelsoft.scano.tools.Enum.Auth.Gender;
import com.camelsoft.scano.tools.Enum.Auth.paymentstate;
import com.camelsoft.scano.tools.util.BaseController;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/admin")
public class adminController extends BaseController {


    @Autowired
    private UserService userService;
    @Autowired
    private companyService companyservice;
    @Autowired
    private noteService noteServices;
    @Autowired
    private CreteriaSearchRepository creteriaSearchRepository;
    @Autowired
    private nfc_cardService nfcCardService;
    @Autowired
    private companyService companyServices;

    @Autowired
    private requestCompanyServices requestcompanyServices;
    @Autowired
    private card_package_service Card_package_service;
    @Autowired
    private FileServices fileServices;
    @Autowired
    private FilesStorageServiceImpl filesStorageService;
    private static final List<String> image_accepte_type = Arrays.asList("JPEG", "jpeg", "svg", "png", "SVG", "PNG", "JPG", "jpg");


    @GetMapping(value = {"/all_users_search"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DynamicResponse>all_users_search(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,  @RequestParam(defaultValue = "false") Boolean city, @RequestParam(required = false) Integer Cityasc, @RequestParam(defaultValue = "0") int timestmpasc, @RequestParam(required = false) String searchphrase, @RequestParam(required = false) step state, @RequestParam(required = false) String type) throws IOException {
        PageImpl<users> user = this.creteriaSearchRepository.UserSearch(new PageConfig(page,size),city,Cityasc,timestmpasc,searchphrase,type,state);
        return new ResponseEntity<>(new DynamicResponse(user.getContent(),user.getNumber(),user.getTotalElements(), user.getTotalPages()), HttpStatus.OK);
    }
    @GetMapping(value = {"/get_weekly_cumulative_amount"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Double> > get_weekly_cumulative_amount (@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) throws IOException {
        List<Double>  user = this.creteriaSearchRepository.soldcards(date);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = {"/all_company_search"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DynamicResponse>all_company_search(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "false") Boolean city, @RequestParam(required = false) Integer Cityasc, @RequestParam(defaultValue = "0") int timestmpasc, @RequestParam(required = false) String searchphrase, @RequestParam(required = false) String type) throws IOException {

        PageImpl<company> user = this.creteriaSearchRepository.CompanySearch(new PageConfig(page,size),city,Cityasc,timestmpasc,searchphrase,type);
        return new ResponseEntity<>(new DynamicResponse(user.getContent(),user.getNumber(),user.getTotalElements(), user.getTotalPages()), HttpStatus.OK);
    }

    @GetMapping(value = {"/all_company_request_search"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DynamicResponse>all_company_request_search(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "false") Boolean city, @RequestParam(required = false) Integer Cityasc, @RequestParam(defaultValue = "0") int timestmpasc, @RequestParam(required = false) String searchphrase, @RequestParam(required = false) requestState state, @RequestParam(required = false) String type) throws IOException {

        PageImpl<company_request> user = this.creteriaSearchRepository.CompanyRequestSearch(new PageConfig(page,size),city,Cityasc,timestmpasc,searchphrase,type,state);
        return new ResponseEntity<>(new DynamicResponse(user.getContent(),user.getNumber(),user.getTotalElements(), user.getTotalPages()), HttpStatus.OK);
    }

    @PostMapping(value = {"/add_card_package"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<card_packages> add_card_package(@ModelAttribute crad_package_request request) throws IOException {
        users user = userService.findByUserName(getCurrentUser().getUsername());

        if (this.Card_package_service.existTop())
            return new ResponseEntity("Uid already exist cant add 2 users to one card : " , HttpStatus.CONFLICT);
        card_packages cd_package = this.Card_package_service.Updatesocialmedia(new card_packages(request.getCard_name(), request.getColors(),request.getStyles(),request.getUnitprice(), request.getCards()));
        return new ResponseEntity<>(cd_package, HttpStatus.OK);
    }

    @PatchMapping(value = {"/update_card_package"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<card_packages> update_card_package(@ModelAttribute crad_package_request request) throws IOException {
        users user = userService.findByUserName(getCurrentUser().getUsername());

        if (!this.Card_package_service.existTop())
            return new ResponseEntity("no card pachage found: " , HttpStatus.CONFLICT);
        card_packages cd_package = this.Card_package_service.findTop();

        if (request.getCard_name()!= null)
            cd_package.setCard_name(request.getCard_name());

        if (request.getUnitprice()!= null)
            cd_package.setUnitprice(request.getUnitprice());

        if (request.getColors()!= null) {
            cd_package.getColors().addAll(request.getColors());
            cd_package.getColors().clear();
        }

        if (request.getStyles()!= null) {
            cd_package.getStyles().addAll(request.getStyles());
            cd_package.getStyles().clear();
        }

        cd_package = this.Card_package_service.Updatesocialmedia(cd_package);


        return new ResponseEntity<>(cd_package, HttpStatus.OK);
    }

    @GetMapping(value = {"/get_card_package"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<card_packages> get_card_package() throws IOException {
        card_packages cd_package = this.Card_package_service.findTop();
        return new ResponseEntity<>(cd_package, HttpStatus.OK);
    }


    @PostMapping(value = {"/adduser"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<users> adduser (@ModelAttribute AdduserRequest request, @RequestParam(value = "file",required = false) MultipartFile file) throws IOException, InterruptedException, MessagingException {
        users user = userService.findbyemail(request.getEmail());
        if (user != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
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

            user = new users(username,request.getEmail(), request.getPassword(), request.getLastname() + request.getFirstname(), request.getLastname(), request.getFirstname(), request.getPhonenumber(), request.getLandline(), request.getFax(), request.getAddresstype(), request.getAddress(),request.getCountry(), request.getArea(), request.getStreet(), request.getBuilding(), request.getFloor(), request.getUnitnumberaddress(), request.getNickname(), request.getCity(), request.getCompany_name(), request.getPosition(), request.getWebsite(), request.getCardemail());

            users result = userService.saveUser(user);

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

                user = this.userService.UpdateUser(user);

                this.Card_package_service.Updatesocialmedia(cd_package);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping(value = {"/getAll_users/{company_id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<users>> getAll_users(@PathVariable Long company_id) {
        if (!this.companyServices.existbyid(company_id))
            return new ResponseEntity("invalid id: "+company_id, HttpStatus.BAD_REQUEST);

        company companie = this.companyServices.findById(company_id);

        Set<users> result = companie.getSubscribers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = {"/add_note/{company_request_id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<note> add_note(@PathVariable Long company_request_id,@RequestParam String content) {
        if (!this.requestcompanyServices.existbyid(company_request_id))
            return new ResponseEntity("invalid id: "+company_request_id, HttpStatus.BAD_REQUEST);

        company_request companie = this.requestcompanyServices.findById(company_request_id);

        note noat = this.noteServices.Update(new note(content,companie));

        return new ResponseEntity<>(noat, HttpStatus.OK);
    }

    @PostMapping(value = {"/update_note/{note_id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<note> update_note(@PathVariable Long note_id,@RequestParam String content) {
        if (!this.noteServices.existbyid(note_id))
            return new ResponseEntity("invalid id: "+note_id, HttpStatus.BAD_REQUEST);

        note companie = this.noteServices.findById(note_id);
        companie.setNote(content);

        note noat = this.noteServices.Update(companie);

        return new ResponseEntity<>(noat, HttpStatus.OK);
    }


    @PostMapping(value = {"/all_notes_by_request/{company_request_id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<note>> all_notes_by_request(@PathVariable Long company_request_id) {
        if (!this.requestcompanyServices.existbyid(company_request_id))
            return new ResponseEntity("invalid id: "+company_request_id, HttpStatus.BAD_REQUEST);

        company_request companie = this.requestcompanyServices.findById(company_request_id);

        List<note> noat = this.noteServices.findAllByCompanie(companie);

        return new ResponseEntity<>(noat, HttpStatus.OK);
    }


    @GetMapping(value = {"/totalcompany"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> totalcompany() {
        Long result = this.companyServices.countAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = {"/countAllUsers"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> countAllUsers() {
        Long result = this.userService.countAllUsers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @GetMapping(value = {"/totalsales"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> sumAmount() {
        Double sumCompanys = this.companyServices.sumNfcCardCash();
        Double sumCards = this.nfcCardService.sumNfcCardCash();

        return new ResponseEntity<>(sumCompanys != null ? sumCompanys : 0 +( sumCards != null ? sumCards : 0), HttpStatus.OK);
    }


    @GetMapping(value = {"/get_company_by/{company_id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<company> get_company_by_id (@PathVariable Long company_id) {

        if (this.companyServices.existbyid(company_id))
            return new ResponseEntity("invalid id " + company_id, HttpStatus.NOT_FOUND);


        company companie = this.companyServices.findById(company_id);

        return new ResponseEntity<>(companie, HttpStatus.OK);
    }

    @GetMapping(value = {"/get_user_by/{user_id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<users> get_user_by (@PathVariable Long user_id) {

        if (this.userService.existbyid(user_id))
            return new ResponseEntity("invalid id " + user_id, HttpStatus.NOT_FOUND);
        users companie = this.userService.findById(user_id);
        return new ResponseEntity<>(companie, HttpStatus.OK);
    }

    @GetMapping(value = {"/totalactivecards"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> totalactivecards () {
        Long sumCards = this.nfcCardService.countAllByState();
        return new ResponseEntity<>(sumCards, HttpStatus.OK);
    }

    @PatchMapping(value = {"/suspend_user/{user_id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<users> suspend_user(@PathVariable Long user_id) throws IOException {
        if (!this.userService.existbyid(user_id))
            return new ResponseEntity("user not found", HttpStatus.NOT_FOUND);
        users user = userService.findById(user_id);
        user.setActive(!user.getActive());
        user = this.userService.UpdateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping(value = {"/update_company_request_state/{request_id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<company_request> update_company_request_state(@PathVariable Long request_id,@RequestParam requestState state) {
        if (!this.requestcompanyServices.existbyid(request_id))
            return new ResponseEntity("invalid id: "+request_id, HttpStatus.BAD_REQUEST);
        company_request req = this.requestcompanyServices.findById(request_id);
        req.setRequeststate(state);
        company_request result = this.requestcompanyServices.Update(req);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
