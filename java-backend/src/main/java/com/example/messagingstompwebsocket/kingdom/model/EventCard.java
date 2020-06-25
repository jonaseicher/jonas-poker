package com.example.messagingstompwebsocket.kingdom.model;

import lombok.Data;

@Data
public class EventCard implements Card {
    int ID;
    String name;
    String description;
    Map<String, String> player2decision;
}