package com.camelsoft.scano.client.services.File;


import com.camelsoft.scano.client.Repository.File.File_Repository;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FileServices {
    @Autowired
    private File_Repository repository;

    public File_model save_file(File_model model){
        return this.repository.save(model);
    }
    public File_model update_file(File_model model){
        return this.repository.save(model);
    }
    public File_model findbyid(Long id){
        try {
            return this.repository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw  new NotFoundException(String.format("No file found with id [%s] in our data base",id));
        }

    }

    public boolean existbyid(Long id){
        try {
            return this.repository.existsById(id);
        }catch (NoSuchElementException ex){
            throw  new NotFoundException(String.format("No file found with id [%s] in our data base",id));
        }

    }
    public void deletebyid(Long id){
        try {
             this.repository.deleteById(id);
        }catch (NoSuchElementException ex){
            throw  new NotFoundException(String.format("No file found with id [%s] in our data base",id));
        }

    }
}
