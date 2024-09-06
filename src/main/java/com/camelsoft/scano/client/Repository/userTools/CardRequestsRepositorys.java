package com.camelsoft.scano.client.Repository.userTools;

import com.camelsoft.scano.client.models.Auth.Tools.CardRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRequestsRepositorys extends JpaRepository<CardRequests,Long> {
}
