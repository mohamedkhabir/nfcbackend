package com.camelsoft.scano.client.Repository.Country;

import com.camelsoft.scano.client.models.country.Translations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationsRepository extends JpaRepository<Translations,Long> {

}
