package com.osullivan.minesweeper;

public class InBoundRuleChecker extends MoveRuleChecker {
  public InBoundRuleChecker(MoveRuleChecker next){
    super(next);
  }

  @Override
  protected String checkCurrentRule(Square square, Board board) {
    String errMsg = "invalid move: out of board";
    if(square.getRow() < 0 || square.getColumn() < 0){
      return errMsg;
    }
    if(square.getRow() >= board.getHeight() || square.getColumn() >= board.getWidth()){
      return errMsg;
    }
    return null;
  }
}
