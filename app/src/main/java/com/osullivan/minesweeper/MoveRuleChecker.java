package com.osullivan.minesweeper;

public abstract class MoveRuleChecker {
  private final MoveRuleChecker next;

  public MoveRuleChecker(MoveRuleChecker next){
    this.next = next;
  }

  protected abstract String checkCurrentRule(Square square, Board board);

  public String checkMove(Square square, Board board){
    String result = this.checkCurrentRule(square, board);
    if(result != null){
      return result;
    }
    if(next != null){
      return next.checkMove(square, board);
    }
    return null;
  }
}
