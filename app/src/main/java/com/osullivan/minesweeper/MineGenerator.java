package com.osullivan.minesweeper;

import java.util.HashSet;
import java.util.Random;

public class MineGenerator {
  private int width;
  private int height;
  private int numberOfMines;
  private HashSet<Square> mines;
  private Random randRow;
  private Random randColumn;

  public MineGenerator(int width, int height, int numberOfMines, int seedWidth, int seedHeight){
    this.width = width;
    this.height = height;
    this.numberOfMines = numberOfMines;
    this.mines = new HashSet<>();
    randRow = new Random(seedHeight);
    randColumn = new Random(seedWidth);
  }

  public MineGenerator(int width, int height, int numberOfMines){
    this.width = width;
    this.height = height;
    this.numberOfMines = numberOfMines;
    this.mines = new HashSet<>();
    randRow = new Random();
    randColumn = new Random();
  }

  private Square generateOneMine(){
    int row = randRow.nextInt(this.height);
    int column = randColumn.nextInt(this.width);

    Square mine = new Square(row, column);
    if(this.mines.contains(mine)){
      mine = this.generateOneMine();
    }
    return mine;
  }

  public HashSet<Square> generateMines(){
    for(int i = 0; i < this.numberOfMines; i++){
      this.mines.add(this.generateOneMine());
    }
    return this.mines;
  }
}
