package com.example.demo.controller;

import com.example.demo.domain.Manager;
import com.example.demo.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerRepository managerRepository;

    @GetMapping("")
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

}
