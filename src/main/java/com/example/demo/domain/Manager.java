package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "managerId")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Manager extends Employee {

    public Manager() {}

    public Manager(String firstName, String lastName, String position, Manager manager) {
        super(firstName, lastName, position, manager);
    }

    @JsonManagedReference
    @OneToMany(mappedBy="manager")
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
