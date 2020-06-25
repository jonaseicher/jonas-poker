package com.example.messagingstompwebsocket.kingdom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.messagingstompwebsocket.kingdom.model.EventCard;
import com.example.messagingstompwebsocket.kingdom.model.Kingdom;
import com.example.messagingstompwebsocket.kingdom.model.Townsman;

import org.springframework.stereotype.Service;


@Service
public class KingdomService {

  List<Kingdom> kingdomList = new ArrayList<Kingdom>();

  public KingdomService() {
    Kingdom kingdom = new Kingdom();
    kingdom.setName("Beacharena");
    Townsman t = new Townsman();
    t.setName("Jerick");
    kingdom.getTownsmen().add(t);
    kingdomList.add(kingdom);
  }

  public List<String> getKingdomList() {
    return kingdomList.stream().map(Kingdom::getName).collect(Collectors.toList());
  }

  public Kingdom getKingdom(String kingdomName) {
    for (Kingdom k : this.kingdomList) {
      if (k.getName().equals(kingdomName)) return k;
    }
    return null;
  }

  public Kingdom joinKingdom(String kingdomName, Townsman townsman) {
    Kingdom kingdom = getKingdom(kingdomName);
    if (kingdom == null) throw new IllegalArgumentException("Kingdom " + kingdomName + " not found.");
    if (kingdom.getTownsmen().contains(townsman)) {
      throw new IllegalStateException("Townsman " + townsman.getName() + " is already part of kingdom " + kingdomName);
    }
    kingdom.getTownsmen().add(townsman);
    return kingdom;
  }



  public Kingdom triggerKingdomEvent(String kingdomName, String event) {
    Kingdom kingdom = getKingdom(kingdomName);
    return kingdom;
    // TODO
  }

public EventCard decide(String kingdomName, int eventId, String decision) {
  return null;

  // TODO
}

}