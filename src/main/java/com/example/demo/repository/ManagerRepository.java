package com.example.demo.repository;

import com.example.demo.domain.Manager;
import org.springframework.stereotype.Repository;


@Repository
public interface ManagerRepository extends CommonEmployeeRepository<Manager> {

}
