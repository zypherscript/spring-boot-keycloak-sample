package com.example.springbootkeycloak.controller;

import com.example.springbootkeycloak.entity.Customer;
import com.example.springbootkeycloak.repository.CustomerRepository;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class WebController {

  private final CustomerRepository customerRepository;

  @JsonView(Customer.ListJsonView.class)
  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public List<Customer> getCustomers() {
    return customerRepository.findAll();
  }
}
