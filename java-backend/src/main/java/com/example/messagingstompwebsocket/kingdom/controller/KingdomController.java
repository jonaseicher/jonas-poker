package com.example.messagingstompwebsocket.kingdom.controller;

import java.util.List;

import com.example.messagingstompwebsocket.kingdom.model.Deck;
import com.example.messagingstompwebsocket.kingdom.model.EventCard;
import com.example.messagingstompwebsocket.kingdom.model.Kingdom;
import com.example.messagingstompwebsocket.kingdom.model.Townsman;
import com.example.messagingstompwebsocket.kingdom.service.KingdomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KingdomController {

  @Autowired
  KingdomService kingdomService;

  @GetMapping("/kingdom")
  public Kingdom getKingdom(@RequestParam String kingdomName) {
    return kingdomService.getKingdom(kingdomName);
  }

  @GetMapping("/kingdomList")
  public List<String> getKingdomList() {
    return kingdomService.getKingdomList();
  }

  @GetMapping("/kingdom/join")
  public Kingdom joinKingdom(@RequestParam String kingdomName, String playerName) {
    Townsman townsman = new Townsman();
    townsman.setName(playerName);
    return kingdomService.joinKingdom(kingdomName, townsman);
  }

  @GetMapping("/kingdom/event")
  public EventCard drawEventCard(@RequestParam String kingdomName) {
    Kingdom kingdom = kingdomService.getKingdom(kingdomName);
    Deck kingdomEvents = kingdom.getKingdomEvents();
    //return deckService.draw(kingdomEvents)
    return new EventCard();
  }






}