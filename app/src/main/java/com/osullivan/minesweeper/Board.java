package com.osullivan.minesweeper;

import java.util.HashMap;
import java.util.HashSet;

public class Board {
  private int width;
  private int height;
  private HashSet<Square> mines;
  private HashSet<Square> flags;
  private HashMap<Square, Integer> steps; // {square: num of mines around it}

  //TODO
  /**
   * default constructor
   * @param width
   * @param height
   * @param numberOfMines
   */
  public Board(int width, int height, int numberOfMines){

  }

  /**
   * test constructor
   * DO NOT use for prod
   * @param width
   * @param height
   * @param mines
   */
  public Board(int width, int height, HashSet<Square> mines){
    this.width = width;
    this.height = height;
    this.mines = mines;
    this.flags = new HashSet<>();
    this.steps = new HashMap<>();
  }

  /**
   * test constructor
   * DO NOT use for prod
   * @param width
   * @param height
   */
  public Board(int width, int height){
    this.width = width;
    this.height = height;
    this.mines = new HashSet<>();
    this.flags = new HashSet<>();
    this.steps = new HashMap<>();
  }

  public int getWidth(){
    return this.width;
  }

  public int getHeight(){
    return this.height;
  }

  public boolean isSquareFlagged(Square square){
    return this.flags.contains(square);
  }

  public boolean isSquareStepped(Square square){
    return this.steps.keySet().contains(square);
  }

  public boolean isSquareMined(Square square){
    return this.mines.contains(square);
  }

  protected int countMineAround(Square square){
    int mineCount = 0;
    int currRow = square.getRow();
    int currCol = square.getColumn();
    MoveRuleChecker checker = new InBoundRuleChecker(null);

    for(int i = currRow - 1; i < currRow + 2; i++){
      for(int j = currCol - 1; j < currCol + 2; j++){
        Square currSquare = new Square(i, j);
        if(checker.checkMove(currSquare, this) == null){
          if(currSquare.equals(square)){
            continue;
          }
          if(this.mines.contains(currSquare)){
            mineCount++;
          }
        }
      }
    }
    return mineCount;
  }

  public String stepOn(Square square, boolean propagateIfNoMine){
    MoveRuleChecker checker = new InBoundRuleChecker(new StepFlagRuleChecker(null));
    String errMsg = checker.checkMove(square, this);
    if(errMsg != null){
      return errMsg;
    }

    int numOfMinesAround = this.countMineAround(square);
    this.steps.put(square, numOfMinesAround);

    if(propagateIfNoMine && numOfMinesAround == 0){
      int currRow = square.getRow();
      int currCol = square.getColumn();
      for(int i = currRow - 1; i < currRow + 2; i++){
        for(int j = currCol - 1; j < currCol + 2; j++){
          Square currSquare = new Square(i, j);
          if(!this.isSquareMined(currSquare)){
            this.stepOn(currSquare, propagateIfNoMine);
          }
        }
      }
    }
    return null;
  }

  public String putFlagOn(Square square){
    MoveRuleChecker checker = new InBoundRuleChecker(new StepFlagRuleChecker(null));
    String errMsg = checker.checkMove(square, this);
    if(errMsg != null){
      return errMsg;
    }
    this.flags.add(square);
    return null;
  }

  public String unflagFrom(Square square){
    MoveRuleChecker checker = new InBoundRuleChecker(new UnflagRuleChecker(null));
    String errMsg = checker.checkMove(square, this);
    if(errMsg != null){
      return errMsg;
    }
    this.flags.remove(square);
    return null;
  }

  public boolean allMinesFlaggedAndSquareStepped(){
    if(!this.flags.equals(this.mines)){
      return false;
    }
    if(this.steps.keySet().size() != this.height * this.width - this.mines.size()){
      return false;
    }
    return true;
  }
}
