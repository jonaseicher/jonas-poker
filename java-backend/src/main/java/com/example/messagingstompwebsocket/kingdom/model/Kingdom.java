package com.example.messagingstompwebsocket.kingdom.model;

import java.util.List;

import lombok.Data;

@Data
public class Kingdom {

  String name;
  List<Townsman> townsmen;
}