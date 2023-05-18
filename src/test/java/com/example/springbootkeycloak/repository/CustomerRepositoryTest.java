package com.example.springbootkeycloak.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbootkeycloak.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  void testFindAllCustomers() {
    var address = "address";
    var name = "name";
    var customer = new Customer();
    customer.setAddress(address);
    customer.setName(name);

    entityManager.persist(customer);
    entityManager.flush();

    var whatWeGet = customerRepository.findAll();
    assertThat(whatWeGet).isNotEmpty();
    assertThat(whatWeGet.get(0).getAddress()).isEqualTo(address);
    assertThat(whatWeGet.get(0).getName()).isEqualTo(name);
  }
}