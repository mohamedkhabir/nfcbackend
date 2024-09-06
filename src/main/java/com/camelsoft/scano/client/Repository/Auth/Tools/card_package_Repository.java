package com.camelsoft.scano.client.Repository.Auth.Tools;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.camelsoft.scano.client.models.Auth.Tools.card_packages;


@Repository
public interface card_package_Repository extends JpaRepository<card_packages,Long> {


    card_packages findTopByOrderByIdDesc();
    Boolean existsTopByOrderByIdDesc();

}
