package com.example.demo;

import com.example.demo.domain.Employee;
import com.example.demo.domain.Manager;
import com.example.demo.repository.EmployeeRepository;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.stream.LongStream;

@EnableTransactionManagement
@SpringBootApplication
public class DemoApplication {

	@Autowired
	private EmployeeRepository employeeRepository;

	@PersistenceUnit
	private EntityManagerFactory emf;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	InitializingBean prepareDatabase() {
		return () -> {

			employeeRepository.save(new Employee("Ruslan", "Abdulov", "dev", null));
			employeeRepository.save(new Employee("Mike", "Brandul", "dev", null));
			employeeRepository.save(new Employee("John", "Silver", "qa", null));

			Manager savedManager =
					employeeRepository.save(new Manager("Alex", "Boyko", "mgr", null));

			LongStream.rangeClosed(1, 3).forEach(
					ind -> employeeRepository.findById(ind)
							.ifPresent(e -> {
								e.setManager(savedManager);
								employeeRepository.save(e);
							}
					)
			);
		};
	}

	@EventListener
	void prepareSearchIndex(ContextRefreshedEvent event) {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(emf.createEntityManager());
			fullTextEntityManager.createIndexer().startAndWait();
		}
		catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the search index: " + e.toString());
		}

	}
}
