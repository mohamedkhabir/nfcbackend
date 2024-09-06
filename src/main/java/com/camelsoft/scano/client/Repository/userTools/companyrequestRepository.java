package com.camelsoft.scano.client.Repository.userTools;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.camelsoft.scano.client.models.Auth.company_request;

@Repository
public interface companyrequestRepository extends JpaRepository<company_request,Long> {

}
