package org.cong.nec.person.service;

import lombok.AllArgsConstructor;
import org.cong.nec.person.model.Person;
import org.cong.nec.person.dto.PersonDTO;
import org.cong.nec.person.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public List<PersonDTO> findAll() {
        return personRepository.findAll().stream()
                .map(person -> PersonDTO.builder()
                        .id(person.getId())
                        .name(person.getName())
                        .build())
                .toList();
    }

    public PersonDTO create(PersonDTO personDTO) {
        Person person = Person.builder()
                .name(personDTO.getName())
                .build();
        Person savedPerson = personRepository.save(person);
        return PersonDTO.builder()
                .id(savedPerson.getId())
                .name(savedPerson.getName())
                .build();
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public PersonDTO update(Long id, PersonDTO personDTO) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        person.setName(personDTO.getName());
        Person updatedPerson = personRepository.save(person);
        return PersonDTO.builder()
                .id(updatedPerson.getId())
                .name(updatedPerson.getName())
                .build();
    }
}
