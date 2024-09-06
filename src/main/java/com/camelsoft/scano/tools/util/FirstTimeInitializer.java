package com.camelsoft.scano.tools.util;

import com.camelsoft.scano.client.Repository.Auth.RoleRepository;
import com.camelsoft.scano.client.Repository.Auth.UserRepository;
import com.camelsoft.scano.client.models.Auth.Role;
import com.camelsoft.scano.client.models.Auth.Tools.card_packages;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.services.Auth.Tools.card_package_service;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.client.services.Country.CountriesServices;
import com.camelsoft.scano.tools.Enum.Auth.Gender;
import com.camelsoft.scano.tools.Enum.Auth.RoleEnum;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FirstTimeInitializer implements CommandLineRunner {
    private final Log logger = LogFactory.getLog(FirstTimeInitializer.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private card_package_service Card_package_service;
    @Autowired
    private CountriesServices countriesServices;

    @Override
    public void run(String... string) throws Exception {
        Roleinit();
        initUser();
        countriesServices.ParseCountry();
//        countriesServices.ParseCountry01();
    }

    void Roleinit() {
        logger.info("No Role found Creating some Role ...");
        for (RoleEnum model : RoleEnum.values()) {
            if (!roleRepository.existsByRole(model.name())) {
                Role rolemodel = new Role();
                rolemodel.setRole(model.name());
                roleRepository.save(rolemodel);
            }
        }
    }

    void initUser() {

         //init user
        if (!this.Card_package_service.existTop())
        this.Card_package_service.Updatesocialmedia(new card_packages());
    }
}
