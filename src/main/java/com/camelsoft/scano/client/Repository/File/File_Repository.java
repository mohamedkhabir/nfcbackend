package com.camelsoft.scano.client.Repository.File;


import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.File.File_model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface File_Repository extends JpaRepository<File_model,Long> {
    boolean existsById(Long id);

}
