package de.shks.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.*;
import java.util.*;

import de.shks.example.demo.entities.*;

@Repository
public interface CountryRepository 
         extends JpaRepository<Country, Long> {

    Optional<Country> findByName(String name);
    Optional<Country> findByAlpha2code(String code);
    Optional<Country> findByAlpha3code(String code);
    List<Country> findAllByRegion(String name);
        
}