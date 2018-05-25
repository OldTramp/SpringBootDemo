package com.example.demo.repository;

import com.example.demo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonEmployeeRepository<T extends Employee> extends JpaRepository<T, Long> {

    List<T> findByLastNameContainingIgnoreCase(String lastName);
}
