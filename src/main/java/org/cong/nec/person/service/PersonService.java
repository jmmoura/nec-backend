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
}
