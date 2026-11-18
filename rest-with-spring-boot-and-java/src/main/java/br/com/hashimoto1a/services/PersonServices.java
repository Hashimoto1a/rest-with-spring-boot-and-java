package br.com.hashimoto1a.services;

import br.com.hashimoto1a.controllers.PersonController;
import br.com.hashimoto1a.data.dto.PersonDTO;
import br.com.hashimoto1a.exception.ResourceNotFoundException;
import br.com.hashimoto1a.model.Person;
import br.com.hashimoto1a.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.hashimoto1a.mapper.ObjectMapper.parseListObject;
import static br.com.hashimoto1a.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;


    public List<PersonDTO> findAll(){
        logger.info("Finding all people");

        var persons = parseListObject(repository.findAll(), PersonDTO.class);
        //persons.forEach(p -> addHateoasLinks(p));
        persons.forEach(this::addHateoasLinks);
        return persons;
    }



    public PersonDTO findById(Long id){
        logger.info("Finding a Person!");

        var entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));

        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }


    public PersonDTO create(PersonDTO person){
        logger.info("Creating a person");

        var entity = parseObject(person, Person.class);

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating a person");

        Person entity  = repository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("delete a person");

        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
