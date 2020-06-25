package com.example.messagingstompwebsocket.kingdom.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Townsman {
  @Id
  String name;
  int wealth;
}