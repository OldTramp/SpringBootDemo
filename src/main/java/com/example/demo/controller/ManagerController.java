package com.example.demo.controller;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("")
    public List<Employee> getAllManagers() {
        return employeeRepository.findAll();
    }

}
