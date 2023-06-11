package com.osullivan.minesweeper;

import java.io.PrintStream;

public abstract class BasicPlayer implements Player{
  protected Board board;
  protected final PrintStream outStream;
  protected boolean isPlaying;

  public BasicPlayer(Board board, PrintStream outStream){
    this.board = board;
    this.outStream = outStream;
    this.isPlaying = true;
  }

  public boolean isPlaying(){
    return this.isPlaying;
  }
}
