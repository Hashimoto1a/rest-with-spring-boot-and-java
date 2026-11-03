package br.com.hashimoto1a.services;

import br.com.hashimoto1a.exception.ResourceNotFoundException;
import br.com.hashimoto1a.model.Person;
import br.com.hashimoto1a.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;


    public List<Person> findAll(){
        logger.info("Finding all people");

        return repository.findAll();
    }



    public Person findById(Long id){
        logger.info("Finding a Person!");

        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));

    }

    public Person create(Person person){
        logger.info("Creating a person");
        return repository.save(person);
    }

    public Person update(Person person){
        logger.info("Updating a person");

        Person entity  = repository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirsName(person.getFirsName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void delete(Long id){
        logger.info("delete a person");

        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);


    }
}
