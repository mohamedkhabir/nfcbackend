package com.camelsoft.scano.client.services.projecttools;

import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.Repository.userTools.CardRequestsRepositorys;
import com.camelsoft.scano.client.models.Auth.Tools.CardRequests;
import com.camelsoft.scano.client.models.UserTools.nfc_card;
import com.camelsoft.scano.tools.Enum.Auth.paymentstate;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CardRequestsServices {

    @Autowired
    private CardRequestsRepositorys repository;



    public CardRequests save(CardRequests card) {
        try {
            return this.repository.save(card);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }




    public List<CardRequests> findAll()
    {
        try {
            return  this.repository.findAll();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public boolean existbyid(Long id) {
        try {
            return this.repository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public CardRequests findById(Long userid) {
        try {
            return this.repository.findById(userid).get();

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }




    public void delete(CardRequests card) {
        try {
            this.repository.delete(card);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }


}
