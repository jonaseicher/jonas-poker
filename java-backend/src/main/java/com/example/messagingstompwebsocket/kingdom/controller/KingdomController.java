package com.example.messagingstompwebsocket.kingdom.controller;

import com.example.messagingstompwebsocket.kingdom.model.Kingdom;
import com.example.messagingstompwebsocket.kingdom.service.KingdomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KingdomController {

  @Autowired
  KingdomService service;

  @GetMapping
  public ResponseEntity<Kingdom> getKingdom(@RequestParam String kingdomName) {
    return ResponseEntity.ok().body(service.getKingdom());
  }

}