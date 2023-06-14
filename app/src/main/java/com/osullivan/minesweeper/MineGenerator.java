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

  private void generatorParamChecker(int width, int height, int numberOfMines){
    if(width <= 0 || height <= 0){
      throw new IllegalArgumentException("invalid input: width and height must be > 0");
    }
    if( numberOfMines < 0){
      throw new IllegalArgumentException(
        "invalid input: number of mines must be > 0");
    }
    if(numberOfMines > width * height){
      throw new IllegalArgumentException("invalid input: too many mines");
    }
  }

  public MineGenerator(int width, int height, int numberOfMines, int seedWidth, int seedHeight){
    this.generatorParamChecker(width, height, numberOfMines);
    this.width = width;
    this.height = height;
    this.numberOfMines = numberOfMines;
    this.mines = new HashSet<>();
    randRow = new Random(seedHeight);
    randColumn = new Random(seedWidth);
  }

  public MineGenerator(int width, int height, int numberOfMines){
    this.generatorParamChecker(width, height, numberOfMines);
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
