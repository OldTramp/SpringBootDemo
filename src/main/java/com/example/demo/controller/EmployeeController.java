package com.example.demo.controller;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;
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
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Api(value="Employees operations", description="Operations with Employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("")
    @ApiOperation(value = "View a list of available Employees", response = List.class)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Employee> getEmployee(@PathVariable long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping("/filter/byLastName")
    @ApiOperation(value = "Filter Employees by partial last name", response = List.class)
    public List<Employee> getEmployeeByLastName(@RequestParam String lastName) {
        return employeeRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    @PostMapping("")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully created")})
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEmployee.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully updated")})
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee, @PathVariable long id) {

        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (!employeeOptional.isPresent())
            return ResponseEntity.notFound().build();

        employee.setId(id);
        employeeRepository.save(employee);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully deleted")})
    public ResponseEntity<Object> deleteEmployee(@PathVariable long id) {
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fake")
    public ResponseEntity<Object> fake() {
        return ResponseEntity.ok().build();
    }
}
