package com.example.springbootkeycloak;

import com.example.springbootkeycloak.entity.Customer;
import com.example.springbootkeycloak.repository.CustomerRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootKeycloakApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootKeycloakApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(CustomerRepository customerRepository) {
    return args -> {
      var customer1 = new Customer();
      customer1.setAddress("1111 foo blvd");
      customer1.setName("Foo Industries");

      var customer2 = new Customer();
      customer2.setAddress("2222 bar street");
      customer2.setName("Bar LLP");

      var customer3 = new Customer();
      customer3.setAddress("33 main street");
      customer3.setName("Big LLC");
      customerRepository.saveAll(List.of(customer1, customer2, customer3));
    };
  }

}
