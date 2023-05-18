package com.example.springbootkeycloak;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbootkeycloak.controller.WebController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootKeycloakApplicationTests {

  @Autowired
  private WebController webController;

  @Test
  void contextLoads() {
    assertThat(webController).isNotNull();
  }

}
