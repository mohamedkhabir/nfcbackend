package com.camelsoft.scano.client.services.projecttools;


import com.camelsoft.scano.client.Repository.userTools.SocialmediaRepository;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.UserTools.socialmedia;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SocialmediaService {
    
    @Autowired
    private SocialmediaRepository socialmediaRepository;


    public socialmedia Updatesocialmedia(socialmedia users) {
        try {
            return this.socialmediaRepository.save(users);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }

    public List<socialmedia> findAll()
    {
        try {
            return  this.socialmediaRepository.findAll();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public List<socialmedia> findAllbyUser(users user)
    {
        try {
            return  this.socialmediaRepository.findAllByUser(user);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }


    public boolean existbyid(Long id) {
        try {
            return socialmediaRepository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public boolean existbyidAnduserid(Long id, users user) {
        try {
            return socialmediaRepository.existsByIdAndUser(id,user);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }


    public socialmedia findById(Long userid) {
        try {
            return socialmediaRepository.findById(userid).get();

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public void deleteById(Long userid) {
        try {
             socialmediaRepository.deleteById(userid);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    
    
    
}
