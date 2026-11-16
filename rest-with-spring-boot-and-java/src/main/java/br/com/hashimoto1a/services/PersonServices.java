package br.com.hashimoto1a.services;

import br.com.hashimoto1a.data.dto.v1.PersonDTO;
import br.com.hashimoto1a.data.dto.v2.PersonDTOv2;
import br.com.hashimoto1a.exception.ResourceNotFoundException;
import br.com.hashimoto1a.mapper.custom.PersonMapper;
import br.com.hashimoto1a.model.Person;
import br.com.hashimoto1a.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.hashimoto1a.mapper.ObjectMapper.parseListObject;
import static br.com.hashimoto1a.mapper.ObjectMapper.parseObject;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;


    public List<PersonDTO> findAll(){
        logger.info("Finding all people");

        return parseListObject(repository.findAll(), PersonDTO.class);
    }



    public PersonDTO findById(Long id){
        logger.info("Finding a Person!");

        var entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));

        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Creating a person");

        var entity = parseObject(person, Person.class);

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTOv2 createV2(PersonDTOv2 person){
        logger.info("Creating a person v2");

        var entity = converter.convertDTOToEntity(person);

        return converter.convertEntityToDTO(repository.save(entity));
    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating a person");

        Person entity  = repository.findById(person.getId()).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("delete a person");

        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);


    }
}
