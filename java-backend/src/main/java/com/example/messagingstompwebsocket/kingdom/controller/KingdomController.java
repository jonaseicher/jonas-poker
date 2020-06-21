package com.example.messagingstompwebsocket.kingdom.controller;

import java.util.List;

import com.example.messagingstompwebsocket.kingdom.model.Deck;
import com.example.messagingstompwebsocket.kingdom.model.EventCard;
import com.example.messagingstompwebsocket.kingdom.model.Kingdom;
import com.example.messagingstompwebsocket.kingdom.model.Townsman;
import com.example.messagingstompwebsocket.kingdom.service.DeckService;
import com.example.messagingstompwebsocket.kingdom.service.KingdomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KingdomController {

  @Autowired
  KingdomService kingdomService;

  @Autowired
  DeckService deckService;

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

  @GetMapping("/kingdom/events/create")
  public EventCard createEventCards(@RequestParam String kingdomName) {
    Kingdom kingdom = kingdomService.getKingdom(kingdomName);
    List<EventCard> kingdomEvents = kingdom.getKingdomEvents();
    deckService.addCards(kingdomEvents, EventCard.class, 5);
    return new EventCard();
  }

  @GetMapping("/kingdom/events")
  public EventCard drawEventCard(@RequestParam String kingdomName) {
    Kingdom kingdom = kingdomService.getKingdom(kingdomName);
    Deck kingdomEvents = kingdom.getKingdomEvents();
    //return deckService.draw(kingdomEvents)
    return new EventCard();
  }






}