package com.camelsoft.scano.client.Repository.Country;

import com.camelsoft.scano.client.models.country.Root;
import com.camelsoft.scano.client.models.country.Timezone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimezoneRepository extends JpaRepository<Timezone,Long> {

    List<Timezone> findAllByRoot(Root root);
    boolean existsByZonename(String name);
    Timezone findTopByZonenameOrderById(String name);
}
