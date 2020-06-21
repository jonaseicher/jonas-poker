package com.example.messagingstompwebsocket.kingdom.model;

import lombok.Data;

@Data
public class PlayerAction {

  Townsman actor;
  String name;
  String description;
}