package com.example.demo.repository;

import com.example.demo.domain.Employee;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends CommonEmployeeRepository<Employee> {

}
