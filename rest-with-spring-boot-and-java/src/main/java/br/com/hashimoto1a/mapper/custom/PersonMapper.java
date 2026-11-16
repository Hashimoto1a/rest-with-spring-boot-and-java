package br.com.hashimoto1a.mapper.custom;

import br.com.hashimoto1a.data.dto.v2.PersonDTOv2;
import br.com.hashimoto1a.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDTOv2 convertEntityToDTO(Person person){
        PersonDTOv2 dto = new PersonDTOv2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthDate(new Date());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());
        return dto;
    }

    public Person convertDTOToEntity(PersonDTOv2 dto){
        Person entity = new Person();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        //entity.setBirthDate(new Date);
        entity.setAddress(dto.getAddress());
        entity.setGender(dto.getGender());
        return entity;
    }
}
