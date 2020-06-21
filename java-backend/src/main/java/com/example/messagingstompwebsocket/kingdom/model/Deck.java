package com.example.messagingstompwebsocket.kingdom.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Deck {

  List<Card> draw = new ArrayList<>();
  List<Card> discard = new ArrayList<>();

}