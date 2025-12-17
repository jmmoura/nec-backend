package org.cong.nec.person.controller;

import lombok.AllArgsConstructor;
import org.cong.nec.person.dto.PersonDTO;
import org.cong.nec.person.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(PersonController.PATH)
@AllArgsConstructor
public class PersonController {

    public static final String PATH = "/api/v1/person";

    private PersonService personService;

    @GetMapping
    public List<PersonDTO> findAll() {
        return personService.findAll();
    }

}
