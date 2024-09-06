package com.camelsoft.scano.client.Repository.Auth;


import com.camelsoft.scano.client.models.Auth.company;
import com.camelsoft.scano.client.models.Auth.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface companyRepository extends JpaRepository<company,Long> {



    company findTopByOrderByIdDesc();

    Boolean existsByPromocode(String promocode);
    company findByPromocode(String promocode);

    boolean existsByEmail(String email);

    @Query(value = "SELECT SUM(amount) FROM company", nativeQuery = true)
    public Double sumNfcCardCash();
}
