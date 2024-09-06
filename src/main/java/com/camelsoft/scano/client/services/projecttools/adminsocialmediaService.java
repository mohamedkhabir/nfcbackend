package com.camelsoft.scano.client.services.projecttools;


import com.camelsoft.scano.client.Repository.userTools.adminsocialmediaRepository;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.UserTools.adminsocialmedia;
import com.camelsoft.scano.client.models.UserTools.socialmedia;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class adminsocialmediaService {
    
    @Autowired
    private adminsocialmediaRepository adminsocialmediarepository;


    public adminsocialmedia Updatesocialmedia(adminsocialmedia users) {
        try {
            return this.adminsocialmediarepository.save(users);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }

    public List<adminsocialmedia> findAll()
    {
        try {
            return  this.adminsocialmediarepository.findAll();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }




    public boolean existbyid(Long id) {
        try {
            return adminsocialmediarepository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }



    public adminsocialmedia findById(Long userid) {
        try {
            return adminsocialmediarepository.findById(userid).get();

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public void deleteById(Long userid) {
        try {
            adminsocialmediarepository.deleteById(userid);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }
    
    
}
