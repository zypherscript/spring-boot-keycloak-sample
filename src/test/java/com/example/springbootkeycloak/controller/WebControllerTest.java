package com.example.springbootkeycloak.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootkeycloak.config.SecurityConfig.GrantedAuthoritiesExtractor;
import com.example.springbootkeycloak.entity.Customer;
import com.example.springbootkeycloak.repository.CustomerRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WebController.class)
class WebControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerRepository customerRepository;

  @Test
  @WithMockUser
  void testGetCustomers() throws Exception {
    var address = "address";
    var name = "name";
    var customer = new Customer();
    customer.setAddress(address);
    customer.setName(name);
    when(customerRepository.findAll()).thenReturn(List.of(customer));

    mockMvc.perform(get("/customers")
            .with(jwt().authorities(new GrantedAuthoritiesExtractor())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].address").value(address))
        .andExpect(jsonPath("$[0].name").value(name));

    verify(customerRepository, times(1)).findAll();
  }
}