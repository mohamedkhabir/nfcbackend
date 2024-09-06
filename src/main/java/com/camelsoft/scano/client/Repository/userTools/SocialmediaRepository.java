package com.camelsoft.scano.client.Repository.userTools;

import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.UserTools.socialmedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialmediaRepository extends JpaRepository<socialmedia,Long> {

    Boolean existsByIdAndUser(Long id, users user);

    List<socialmedia> findAllByUser(users user);

}
