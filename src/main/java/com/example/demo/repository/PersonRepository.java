package com.example.demo.repository;

import com.example.demo.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p " +
            "WHERE lower(p.firstName) LIKE CONCAT('%', lower(:name), '%') " +
            "   OR lower(p.lastName) LIKE CONCAT('%', lower(:name), '%') ")
    List<Person> findByLastNameOrFirstName(String name);

    @Query("SELECT p FROM Person p WHERE year(p.birthDate) - year(CURRENT_DATE) BETWEEN :ageFrom AND :ageTo")
    List<Person> findByAgeWithin(int ageFrom, int ageTo);
}
