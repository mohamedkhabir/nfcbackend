package com.camelsoft.scano.client.Repository.Country;


import com.camelsoft.scano.client.models.country.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RootRepository extends JpaRepository<Root,Long> {
    boolean existsByName(String name);
    Page<Root> findAll(Pageable pageable);
    Page<Root> findAllByNameContaining(String name,Pageable pageable);
    Root findByName(String name);
}
