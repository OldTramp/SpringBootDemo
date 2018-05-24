package com.example.demo.controller;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Employee> getEmployee(@PathVariable long id) {
        return employeeRepository.findById(id);
    }
    //TODO /search/byLastName ???
    @GetMapping("/filter/byLastName")
    public List<Employee> getEmployeeByLastName(@RequestParam String lastName) {
        return employeeRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeRepository.deleteById(id);
    }

    @GetMapping("/fake")
    public ResponseEntity<Object> fake() {
        return ResponseEntity.ok().build();
    }
}
