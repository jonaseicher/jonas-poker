package com.example.messagingstompwebsocket.kingdom.service;

import com.example.messagingstompwebsocket.kingdom.model.Kingdom;
import com.example.messagingstompwebsocket.kingdom.model.Townsman;

import org.springframework.stereotype.Service;

@Service
public class KingdomService {

  Kingdom kingdom;

  public KingdomService() {
    kingdom = new Kingdom();
    kingdom.setName("Beacharena");
    kingdom.getTownsmen().add(new Townsman());
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

}