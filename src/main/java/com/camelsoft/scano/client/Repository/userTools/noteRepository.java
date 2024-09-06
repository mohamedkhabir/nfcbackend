package com.camelsoft.scano.client.Repository.userTools;

import com.camelsoft.scano.client.models.Auth.Tools.note;
import com.camelsoft.scano.client.models.Auth.company;
import com.camelsoft.scano.client.models.Auth.company_request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface noteRepository extends JpaRepository<note,Long> {


List<note> findAllByCompanie(company_request companie);

}
