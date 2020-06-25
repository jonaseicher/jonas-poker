package com.example.messagingstompwebsocket.kingdom.controller;

import java.util.List;

import com.example.messagingstompwebsocket.kingdom.model.Card;
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
  public Deck createEventCards(@RequestParam String kingdomName) {
    Kingdom kingdom = kingdomService.getKingdom(kingdomName);
    Deck kingdomEvents = kingdom.getKingdomEvents();
    deckService.addCards(kingdomEvents, EventCard.class, 5);
    return kingdomEvents;
  }

  @GetMapping("/kingdom/events/draw")
  public EventCard drawEventCard(@RequestParam String kingdomName) {
    Kingdom kingdom = kingdomService.getKingdom(kingdomName);
    Deck kingdomEvents = kingdom.getKingdomEvents();
    return (EventCard)deckService.drawCard(kingdomEvents);
  }

  @GetMapping("/kingdom/events/decide")
  public EventCard decideEventCard(@RequestParam String kingdomName, int eventId, String playerName, String decision) {
    // Kingdom kingdom = kingdomService.getKingdom(kingdomName);
    // EventCard event = kingdomService.getEvent(kingdom, eventId);
    EventCard event = kingdomService.decide(kingdomName, eventId, decision);
    return event;
  }

  @GetMapping("/kingdom/events/evaluate")
  public void evaluateEventCard(@RequestParam String kingdomName, int eventID) {
    Kingdom kingdom = kingdomService.getKingdom(kingdomName);
  }






}