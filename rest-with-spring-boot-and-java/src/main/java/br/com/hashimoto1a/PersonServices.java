package br.com.hashimoto1a;

import br.com.hashimoto1a.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<Person> findAll(){
        logger.info("Finding all people");
        List<Person> persons = new ArrayList<Person>();
        for(int i = 0; i < 8; i++){
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }



    public Person findById(String id){
        logger.info("Finding a Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirsName("cavalo");
        person.setLastName("detresPerna");
        person.setAddress("xiquexique - ba - br");
        person.setGender("outros");
        return person;
    }

    public Person create(Person person){
        logger.info("Creating a person");
        return person;
    }

    public Person update(Person person){
        logger.info("Updating a person");
        return person;
    }

    public void delete(String id){
        logger.info("delete a person");
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirsName("fistname" + i);
        person.setLastName("lastname" + i);
        person.setAddress("some address in br");
        person.setGender("outros");
        return person;
    }
}
