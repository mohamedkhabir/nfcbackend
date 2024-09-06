package com.camelsoft.scano.client.services.Auth.Tools;

import com.camelsoft.scano.client.Repository.Auth.Tools.card_package_Repository;
import com.camelsoft.scano.client.models.Auth.Tools.card_packages;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class card_package_service {

@Autowired
   private card_package_Repository cardPackageRepository;



    public card_packages Updatesocialmedia(card_packages users) {
        try {
            return this.cardPackageRepository.save(users);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }

    public List<card_packages> findAll()
    {
        try {
            return  this.cardPackageRepository.findAll();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }




    public boolean existbyid(Long id) {
        try {
            return this.cardPackageRepository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public card_packages findTop()
    {
        try {
            return  this.cardPackageRepository.findTopByOrderByIdDesc();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public Boolean existTop()
    {
        try {
            return  this.cardPackageRepository.existsTopByOrderByIdDesc();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public card_packages findById(Long userid) {
        try {
            return this.cardPackageRepository.findById(userid).get();

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public void deleteById(Long userid) {
        try {
            this.cardPackageRepository.deleteById(userid);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }


}
