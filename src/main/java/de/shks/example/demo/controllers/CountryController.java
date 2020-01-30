package de.shks.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.shks.example.demo.entities.Country;
import de.shks.example.demo.repositories.CountryRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    @Autowired
    CountryRepository repository;
    
    @GetMapping
    public List<Country> list(){
        return repository.findAll();
    }

    @GetMapping("{id}") 
    public ResponseEntity<?> getById(@PathVariable Long id) { 
       Optional<Country> elem = repository.findById(id); 
       if(!elem.isPresent()) 
       { 
           return new ResponseEntity<String>("Element not Found", HttpStatus.NOT_FOUND); 
       } 
       return new ResponseEntity<Country>(elem.get(), HttpStatus.OK); 
    } 
   
    @DeleteMapping("/{id}")
    public void deleteElement(@PathVariable final long id) {
        repository.deleteById(id);
    }

    @PostMapping("")
    public ResponseEntity<?> createElement(@RequestBody final Country element){
        final Country savedElement = repository.save(element);

        final URI location = 
             ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedElement.getId()).toUri();
    
        return ResponseEntity.created(location).build();
    
    }

    // ---------------------------------------------------------------------------------
    
    @GetMapping("/find")
    public ResponseEntity<?> byCode( @RequestParam(value="code") final String code ){
        final Optional<Country> element;
        if(code.length()==2){
            element = repository.findByAlpha2code(code.toUpperCase());
        }else{
            element = repository.findByAlpha3code(code.toUpperCase());
        }

        
        if(!element.isPresent()){
            return new ResponseEntity<String>("Element not Found", HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<Country>(element.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody final Country element, @PathVariable final long id) {
        final Optional<Country> elementOptional = repository.findById(id);
 
        if (!elementOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
 
        element.setId(id);
        
        repository.save(element);
 
        return ResponseEntity.noContent().build();
    }

}