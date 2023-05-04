package com.example.springbootkeycloak.repository;

import com.example.springbootkeycloak.entity.Customer;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  @Override
  List<Customer> findAll();
}
