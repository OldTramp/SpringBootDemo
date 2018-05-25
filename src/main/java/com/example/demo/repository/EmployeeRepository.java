package com.example.demo.repository;

import com.example.demo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "/workers")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByLastNameContainingIgnoreCase(String lastName);
}
