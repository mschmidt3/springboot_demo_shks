package de.shks.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.shks.example.demo.entities.Country;
import de.shks.example.demo.repositories.CountryRepository;

@Component
public class CountryDataInitializer implements ApplicationRunner{
    @Autowired
    CountryRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        repository.save(new Country("Deutschland", "DE", "DEU", "Berlin", null) );
   
        repository.save(new Country("Frankreich", "FR", "FRA", null, null) );
        repository.save(new Country("England", "EN", "ENG", null, null) );


    }

}