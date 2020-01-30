package de.shks.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.shks.example.demo.entities.Country;
import de.shks.example.demo.repositories.CountryRepository;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    Logger log = LoggerFactory.getLogger(CountryController.class);

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

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> partialUpdateGeneric(
        @RequestBody final Map<String, Object> updates,
        @PathVariable("id") final Long id) {
            final Optional<Country> elementOptional = repository.findById(id);
 
            if (!elementOptional.isPresent()){
                return ResponseEntity.notFound().build();
            }
            final Country element = elementOptional.get();

            element.setId(id);
            for(final Map.Entry<String, Object> entry : updates.entrySet()){
                log.info("   key:" + entry.getKey() + " val:"+ entry.getValue());

                if(entry.getKey().equals("population")){
                    element.setPopulation( Long.parseLong(entry.getValue().toString()) );
                }
                if(entry.getKey().equals("alpha2code")){
                    element.setAlpha2code( entry.getValue().toString() );
                }
                if(entry.getKey().equals("alpha3code")){
                    element.setAlpha3code( entry.getValue().toString() );
                }
                if(entry.getKey().equals("capital")){
                    element.setCapital( entry.getValue().toString() );
                }
                if(entry.getKey().equals("region")){
                    element.setRegion( entry.getValue().toString() );
                }
                if(entry.getKey().equals("subregion")){
                    element.setSubregion( entry.getValue().toString() );
                }
                if(entry.getKey().equals("flag")){
                    element.setFlag( entry.getValue().toString() );
                }
                if(entry.getKey().equals("subregion")){
                    element.setContinent( entry.getValue().toString() );
                }

            }
            repository.save(element);
            
        return ResponseEntity.ok("resource updated");
    }


}