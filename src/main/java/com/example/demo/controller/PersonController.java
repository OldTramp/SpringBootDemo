package com.example.demo.controller;

import com.example.demo.domain.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.PersonSearch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/persons")
@Api(value="Persons operations", description="Operations with Persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonSearch personSearch;

    @GetMapping("")
    @ApiOperation(value = "View a list of available Persons", response = List.class)
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable long id) {
        return personRepository.getOne(id);
    }

    @GetMapping("/filter/byName")
    @ApiOperation(value = "Filter Persons by partial name", response = List.class)
    public List<Person> getPersonByLastName(@RequestParam String name) {
        return personRepository.findByLastNameOrFirstName(name);
    }

    @GetMapping("/filter/byAge")
    @ApiOperation(value = "Filter Persons by age", response = List.class)
    public List<Person> getPersonByLastName(@RequestParam("from") Integer from, @RequestParam("to") Integer to) {
        return personRepository.findByAgeWithin(from, to);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Full text search", response = List.class)
    public List<Person> search(@RequestParam String q) {
        return personSearch.search(q);
    }

    @PostMapping("")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully created")})
    public ResponseEntity<Object> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPerson.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully updated")})
    public ResponseEntity<Object> updatePerson(@RequestBody Person person, @PathVariable long id) {
        Person dbEntity = personRepository.getOne(id);

        if (dbEntity != null)
            return ResponseEntity.notFound().build();

        person.setId(id);
        personRepository.save(person);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully deleted")})
    public ResponseEntity<Object> deletePerson(@PathVariable long id) {
        personRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

}
