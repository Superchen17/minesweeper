package com.osullivan.minesweeper;

import java.util.HashSet;

public class MineGenerator {
  private HashSet<Square> mines;

  public MineGenerator(int width, int height, int seed){
    this.mines = new HashSet<>();
  }

  public HashSet<Square> getMines(){
    return this.mines;
  }
}
