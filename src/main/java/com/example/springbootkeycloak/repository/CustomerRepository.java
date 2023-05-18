package com.example.springbootkeycloak.repository;

import com.example.springbootkeycloak.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
