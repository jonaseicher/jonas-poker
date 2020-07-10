package com.example.messagingstompwebsocket.kingdom.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class EventCard implements Card {
    int ID;
    String name;
    String description;
    Map<String, String> player2decision = new HashMap<>();
}