package com.camelsoft.scano.client.services.projecttools;


import com.camelsoft.scano.client.Repository.userTools.noteRepository;
import com.camelsoft.scano.client.models.Auth.Tools.note;
import com.camelsoft.scano.client.models.Auth.company;
import com.camelsoft.scano.client.models.Auth.company_request;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class noteService {


    @Autowired
    private noteRepository noterepository;



    public note Update(note request) {
        try {
            return this.noterepository.save(request);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }
    }

    public List<note> findAll()
    {
        try {
            return  this.noterepository.findAll();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }


    public List<note> findAllByCompanie(company_request cp)
    {
        try {
            return  this.noterepository.findAllByCompanie(cp);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }



    public boolean existbyid(Long id) {
        try {
            return noterepository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }



    public note findById(Long userid) {
        try {
            return noterepository.findById(userid).get();

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public void deleteById(Long userid) {
        try {
            noterepository.deleteById(userid);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }


}
