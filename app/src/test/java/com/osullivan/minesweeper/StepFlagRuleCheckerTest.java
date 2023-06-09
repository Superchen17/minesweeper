package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class StepFlagRuleCheckerTest {
  @Test
  public void test_checkCurrentRule_valid(){
    MoveRuleChecker checker = new StepFlagRuleChecker(null);
    Board board = new Board(5, 10);

    board.stepOn(new Square(3, 3), false);

    String errMsg;
    errMsg = checker.checkMove(new Square(4, 5), board);
    assertNull(errMsg);
    errMsg = checker.checkMove(new Square(5, 4), board);
    assertNull(errMsg);
  }

  @Test
  public void test_checkCurrentRule_invalid(){
    MoveRuleChecker checker = new StepFlagRuleChecker(null);

    HashSet<Square> mines = new HashSet<>();
    mines.add(new Square(4, 3));
    Board board = new Board(5, 10, mines);

    board.stepOn(new Square(3, 2), false);
    board.putFlagOn(new Square(2, 1));

    String actualErrMsg;
    actualErrMsg = checker.checkMove(new Square(1, 1), board);
    assertNull(actualErrMsg);
    actualErrMsg = checker.checkMove(new Square(3, 2), board);
    assertEquals("invalid move: the square has been stepped", actualErrMsg);
    actualErrMsg = checker.checkMove(new Square(2, 1), board);
    assertEquals("invalid move: the square is flagged", actualErrMsg);

  }
}
