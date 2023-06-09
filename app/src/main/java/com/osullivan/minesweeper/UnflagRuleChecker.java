package com.osullivan.minesweeper;

public class UnflagRuleChecker extends MoveRuleChecker{
  public UnflagRuleChecker(MoveRuleChecker next){
    super(next);
  }

  @Override
  protected String checkCurrentRule(Square square, Board board) {
    if(!board.isSquareFlagged(square)){
      return "invalid move: the square is not flagged";
    }
    if(board.isSquareStepped(square)){
      return "invalid move: the square has been stepped";
    }
    return null;
  }
} 
