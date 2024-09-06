package com.camelsoft.scano.client.Repository.Country;

import com.camelsoft.scano.client.models.country.City;
import com.camelsoft.scano.client.models.country.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {

    List<State> findAllByCities(City city);
    State findTopByNameOrderById(String name);

}
