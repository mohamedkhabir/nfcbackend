package com.camelsoft.scano.client.services.Auth;

import com.camelsoft.scano.client.Repository.Auth.companyRepository;
import com.camelsoft.scano.client.models.Auth.company;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class companyService {

    @Autowired
    private companyRepository repository;


    public company UpdateCompany(company users) {
        try {
            return repository.save(users);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }

    public List<company> findAll()
    {
        try {
            return  this.repository.findAll();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public Long countAll()
    {
        try {
            return  this.repository.count();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public Double sumNfcCardCash()
    {
        try {
            return  this.repository.sumNfcCardCash();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public company findTop()
    {
        try {
            return  this.repository.findTopByOrderByIdDesc();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public boolean existByPromocode(String promocode) {
        try {
            return repository.existsByPromocode(promocode);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }
    public company findByPromocode(String promocode) {
        try {
            return repository.findByPromocode(promocode);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public boolean existbyid(Long id) {
        try {
            return repository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public company findById(Long userid) {
        try {
            return repository.findById(userid).get();

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }
}
