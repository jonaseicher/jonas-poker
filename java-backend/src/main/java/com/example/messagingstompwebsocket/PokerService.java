package com.example.messagingstompwebsocket;

import java.util.ArrayList;
import java.util.List;

import com.example.messagingstompwebsocket.pokerlib.TexasHoldemGame;

import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class PokerService {

  @Getter
  List<TexasHoldemGame> tables = new ArrayList<>();

  public PokerService() {
    TexasHoldemGame table1 = new TexasHoldemGame();
    table1.setTableName("Beginners Table");
    this.tables.add(table1);
    TexasHoldemGame table2 = new TexasHoldemGame();
    table2.setTableName("Table-1337");
    this.tables.add(table2);
    TexasHoldemGame table3 = new TexasHoldemGame();
    table3.setTableName("PrOz 0nlY!");
    this.tables.add(table3);
    TexasHoldemGame table4 = new TexasHoldemGame();
    table4.setTableName("ALL YOUR BASE");
    this.tables.add(table4);
  }

  public TexasHoldemGame getTable(String tableName) {
    for (TexasHoldemGame table: tables) {
      if (table.getTableName().equals(tableName)) return table;
    }
    return null;
  }

}
