package com.camelsoft.scano.client.services.projecttools;
import com.camelsoft.scano.client.Repository.userTools.companyrequestRepository;
import com.camelsoft.scano.client.models.Auth.company_request;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.UserTools.socialmedia;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class requestCompanyServices {

    @Autowired
    private companyrequestRepository  companyrequestrepository;

    public company_request Update(company_request request) {
        try {
            return this.companyrequestrepository.save(request);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }

    public List<company_request> findAll()
    {
        try {
            return  this.companyrequestrepository.findAll();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }



    public boolean existbyid(Long id) {
        try {
            return companyrequestrepository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }



    public company_request findById(Long userid) {
        try {
            return companyrequestrepository.findById(userid).get();

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public void deleteById(Long userid) {
        try {
            companyrequestrepository.deleteById(userid);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

}
