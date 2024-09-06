package com.camelsoft.scano.client.Repository.Auth;


import com.camelsoft.scano.client.models.Auth.UserDevice;
import com.camelsoft.scano.client.models.Auth.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice,Long> {
    Optional<UserDevice> findById(Long id);
    Optional<UserDevice> findByToken(String token);
    List<UserDevice> findAllByUser(users user);
    List<UserDevice> findAllByUserOrderByTimestmpDesc(users user);
}
