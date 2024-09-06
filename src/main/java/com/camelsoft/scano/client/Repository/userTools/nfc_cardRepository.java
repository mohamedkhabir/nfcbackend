package com.camelsoft.scano.client.Repository.userTools;

import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.UserTools.nfc_card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface nfc_cardRepository extends JpaRepository<nfc_card,Long> {

    boolean existsByUid(String uid);
    nfc_card findByUid(String uid);
    nfc_card findTopByUser_IdOrderByTimestmpDesc(Long id);
    List<nfc_card> findAllByUserOrderByTimestmpDesc(users user);
    Boolean existsByUser_Id(Long id);
    Long countAllByState(states state);

    @Query(value = "SELECT SUM(amount) FROM nfc_card AS w  where  (w.paiementstate = :paymentstat)", nativeQuery = true)
    public Double sumNfcCardCash( @Param("paymentstat") int paymentstat);



}
