package com.example.messagingstompwebsocket.kingdom.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Deck<T extends Card> {

  List<T> draw = new ArrayList<>();
  List<T> discard = new ArrayList<>();

}