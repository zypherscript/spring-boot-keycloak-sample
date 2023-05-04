package com.example.springbootkeycloak.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @JsonView(Customer.ListJsonView.class)
  private String name;
  @JsonView(Customer.ListJsonView.class)
  private String address;

  public static class ListJsonView {

    private ListJsonView() {
    }
  }
}
