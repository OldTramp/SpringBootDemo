package com.example.demo;

import com.example.demo.domain.Address;
import com.example.demo.domain.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.text.SimpleDateFormat;

@EnableTransactionManagement
@SpringBootApplication
public class DemoApplication {

	@Autowired
	private PersonRepository personRepository;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	InitializingBean prepareData() {
		return () -> {
			personRepository.save(
					new Person("Ruslan", "Abdulov", "AB2", "Dev", formatter.parse("1988-02-10"), "ratioff@gmail.com",
							new Address("410009", "Russia", "Saratov", "Molochnaya", "5/13", "99")));

			personRepository.save(
					new Person("Ivan", "Ivanov", "AA1", "QA", formatter.parse("1965-05-26"), "aaa@mail.ru",
							new Address("143350", "Russia", "Moscow", "Lenina", "12", "3")));

		};
	}

}
