package com.camelsoft.scano.client.Repository.Notifications;


import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.notification.notificationmodel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface notificationrepository extends JpaRepository<notificationmodel,Long> {
    Page<notificationmodel> findAllByReciverOrderByTimestmpDesc(Pageable pageable, users user);
    Page<notificationmodel> findAllByReciverAndStatus(Pageable pageable, users user, states status);
}
