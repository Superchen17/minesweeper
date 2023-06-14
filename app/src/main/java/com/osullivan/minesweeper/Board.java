package com.osullivan.minesweeper;

import java.util.HashMap;
import java.util.HashSet;

public class Board {
  private int width;
  private int height;
  private HashSet<Square> mines;
  private HashSet<Square> flags;
  private HashMap<Square, Integer> steps; // {square: num of mines around it}

  private void squareParamChecker(int width, int height, int numberOfMines){
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

  /**
   * constructor for random mine generation
   * @param width
   * @param height
   * @param numberOfMines
   */
  public Board(int width, int height, int numberOfMines){
    this.squareParamChecker(width, height, numberOfMines);
    this.width = width;
    this.height = height;
    this.flags = new HashSet<>();
    this.steps = new HashMap<>();

    MineGenerator generator = new MineGenerator(width, height, numberOfMines);
    this.mines = generator.generateMines();
  }

  /**
   * constructor with seeds for mine generation
   * @param width
   * @param height
   * @param numberOfMines
   * @param seedWidth
   * @param seedHeight
   */
  public Board(int width, int height, int numberOfMines, int seedWidth, int seedHeight){
    this.squareParamChecker(width, height, numberOfMines);
    this.width = width;
    this.height = height;
    this.flags = new HashSet<>();
    this.steps = new HashMap<>();

    MineGenerator generator = new MineGenerator(width, height, numberOfMines, seedWidth, seedHeight);
    this.mines = generator.generateMines();
  }

  /**
   * test constructor
   * DO NOT use for prod
   * @param width
   * @param height
   * @param mines
   */
  public Board(int width, int height, HashSet<Square> mines){
    this.squareParamChecker(width, height, mines.size());
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
    this.squareParamChecker(width, height, 0);
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
    if(this.isSquareMined(square)){
      return null;
    }

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

  public boolean hasSteppedOnMine(){
    HashSet<Square> steppedAndMined = new HashSet<>(this.steps.keySet());
    steppedAndMined.retainAll(this.mines);
    return steppedAndMined.size() > 0;
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
