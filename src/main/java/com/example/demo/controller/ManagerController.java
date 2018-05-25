package com.example.demo.controller;

import com.example.demo.domain.Employee;
import com.example.demo.domain.Manager;
import com.example.demo.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerRepository managerRepository;

    @GetMapping("")
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Manager> getManager(@PathVariable long id) {
        return managerRepository.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<Object> createManager(@RequestBody Manager manager) {
        Employee savedEmployee = managerRepository.save(manager);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEmployee.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateManager(@RequestBody Manager manager, @PathVariable long id) {

        Optional<Manager> managerOptional = managerRepository.findById(id);

        if (!managerOptional.isPresent())
            return ResponseEntity.notFound().build();

        manager.setId(id);
        managerRepository.save(manager);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteManager(@PathVariable long id) {
        managerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
