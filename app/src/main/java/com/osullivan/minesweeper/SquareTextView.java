package com.osullivan.minesweeper;

public class SquareTextView {
  private Character empty;
  private Character flagged;
  private Character mined;
  private Character missed;

  public SquareTextView(Character empty, Character flagged, Character mined, Character missed){
    this.empty = empty;
    this.flagged = flagged;
    this.mined = mined;
    this.missed = missed;
  }

  public String display(Square square, Board board, boolean hasLost){
    if(!hasLost){
      if(board.isSquareStepped(square)){
        return Integer.toString(board.countMineAround(square));
      }
      else if(board.isSquareFlagged(square)){
        return this.flagged.toString();
      }
      else{
        return this.empty.toString();
      }
    }
    else{
      if(board.isSquareMined(square)){
        if(board.isSquareFlagged(square)){
          return this.flagged.toString();
        }
        else{
          return this.mined.toString();
        }
      }
      else if(board.isSquareFlagged(square)){
        return this.missed.toString();
      }
      else{
        return this.empty.toString();
      }
    }
  }
}
