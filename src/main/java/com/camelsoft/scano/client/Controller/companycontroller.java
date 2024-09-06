package com.camelsoft.scano.client.Controller;


import com.camelsoft.scano.client.Requests.Auth.companyrequest;
import com.camelsoft.scano.client.models.Auth.company;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.client.services.Auth.companyService;
import com.camelsoft.scano.tools.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/company")
public class companycontroller extends BaseController {

    @Autowired
    private companyService companyServices;

    private static final List<String> image_accepte_type = Arrays.asList("JPEG", "jpeg", "svg", "png", "SVG", "PNG", "JPG", "jpg");

    @Autowired
    private UserService userService;

    @PostMapping(value = {"/create"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<company> create(@ModelAttribute companyrequest request) {

        company usersList = this.companyServices.findTop();
        Long lastuserid =  usersList == null ? 0l : usersList.getId() + 1;

        if (request == null) {
            return new ResponseEntity("data missing", HttpStatus.CONFLICT);
        }
        String username = userService.GenerateUserName(request.getCompanyname(), lastuserid);
        company companie = new company(
                request.getEmail(),
                request.getCompanyname(),
                request.getPhonenumber(),
                request.getSecondphonenumber(),
                request.getCards(),
                request.getAmount(),
                username,request.getCity(), request.getAddress()
        );
        company result = companyServices.UpdateCompany(companie);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = {"/GetAll"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<company>> GetAll() {


        List<company> result = companyServices.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}



