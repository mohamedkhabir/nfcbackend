package com.camelsoft.scano.client.services.projecttools;

import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.Repository.userTools.nfc_cardRepository;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.UserTools.nfc_card;
import com.camelsoft.scano.client.models.UserTools.socialmedia;
import com.camelsoft.scano.client.services.Auth.UserService;
import com.camelsoft.scano.tools.Enum.Auth.paymentstate;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class nfc_cardService {
    private final Log logger = LogFactory.getLog(nfc_cardService.class);

    @Autowired
    private nfc_cardRepository nfcCardRepository;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public CompletableFuture<String> runAsyncTask(String uid) {
        return CompletableFuture.supplyAsync(() -> {

            nfc_card card = this.nfcCardRepository.findByUid(uid);

            card.IncScans();

            this.nfcCardRepository.save(card);

            // perform some task and return the result
            logger.info("done !");
            return "Done !";
        }, executorService);
    }


    public nfc_card save(nfc_card card) {
        try {
            return this.nfcCardRepository.save(card);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }




    public List<nfc_card> findAll()
    {
        try {
            return  this.nfcCardRepository.findAll();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public boolean existbyid(Long id) {
        try {
            return this.nfcCardRepository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public boolean existbyUid(String uid) {
        try {
            return this.nfcCardRepository.existsByUid(uid);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public nfc_card findByUid(String uid) {
        try {
            return this.nfcCardRepository.findByUid(uid);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public nfc_card findById(Long userid) {
        try {
            return this.nfcCardRepository.findById(userid).get();

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public nfc_card findByUser(Long userid) {
        try {
            return this.nfcCardRepository.findTopByUser_IdOrderByTimestmpDesc(userid);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }
    public List<nfc_card> findAllByUser(users userid) {
        try {
            return this.nfcCardRepository.findAllByUserOrderByTimestmpDesc(userid);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }
    public Boolean existByUser_Id(Long userid) {
        try {
            return this.nfcCardRepository.existsByUser_Id(userid);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public Double sumNfcCardCash() {
        try {
            return this.nfcCardRepository.sumNfcCardCash(paymentstate.PAID.ordinal());

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }
    public Long countAllByState() {
        try {
            return this.nfcCardRepository.countAllByState(states.DELIVERED);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public void delete(nfc_card card) {
        try {
            this.nfcCardRepository.delete(card);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }


}
