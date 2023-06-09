package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class UnflagRuleCheckerTest {
  @Test
  public void test_checkCurrentRule_valid(){
    MoveRuleChecker checker = new UnflagRuleChecker(null);
    Board board = new Board(5, 10);

    board.putFlagOn(new Square(3, 4));
    board.putFlagOn(new Square(2, 3));

    String errMsg;
    errMsg = checker.checkCurrentRule(new Square(3, 4), board);
    assertNull(errMsg);
    errMsg = checker.checkCurrentRule(new Square(2, 3), board);
    assertNull(errMsg);
  }

  @Test
  public void test_checkCurrentRule_invalid(){
    MoveRuleChecker checker = new UnflagRuleChecker(null);
    Board board = new Board(5, 10);

    board.putFlagOn(new Square(3, 4));
    board.stepOn(new Square(2, 3), false);

    String errMsg;
    errMsg = checker.checkCurrentRule(new Square(1, 1), board);
    assertEquals("invalid move: the square is not flagged", errMsg);

  }
}
