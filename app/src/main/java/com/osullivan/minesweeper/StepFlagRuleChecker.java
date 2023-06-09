package com.osullivan.minesweeper;

public class StepFlagRuleChecker extends MoveRuleChecker{
  public StepFlagRuleChecker(MoveRuleChecker next){
    super(next);
  }

  @Override
  protected String checkCurrentRule(Square square, Board board) {
    if(board.isSquareFlagged(square)){
      return "invalid move: the square is flagged";
    }
    if(board.isSquareStepped(square)){
      return "invalid move: the square has been stepped";
    }
    return null;
  }
}
