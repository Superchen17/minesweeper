package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class InBoundRuleCheckerTest {
  @Test
  public void test_checkCurrentRule_valid(){
    Board board = new Board(5, 10);
    MoveRuleChecker checker = new InBoundRuleChecker(null);

    String errMsg;
    errMsg = checker.checkMove(new Square(3, 4), board); // middle
    assertNull(errMsg);
    errMsg = checker.checkMove(new Square(3, 0), board); // left border
    assertNull(errMsg);
    errMsg = checker.checkMove(new Square(3, 4), board); // right border
    assertNull(errMsg);
    errMsg = checker.checkMove(new Square(9, 3), board); // top border
    assertNull(errMsg);
    errMsg = checker.checkMove(new Square(0, 4), board); // bottom border
    assertNull(errMsg);
  }

  @Test
  public void test_checkCurrentRule_invalid(){
    Board board = new Board(5, 10);
    MoveRuleChecker checker = new InBoundRuleChecker(null);

    String errMsgExpected = "invalid move: out of board";
    String errMsgActual;
    errMsgActual = checker.checkMove(new Square(3, -1), board); // over left
    assertEquals(errMsgExpected, errMsgActual);
    errMsgActual = checker.checkMove(new Square(3, 5), board); // over right
    assertEquals(errMsgExpected, errMsgActual);
    errMsgActual = checker.checkMove(new Square(10, 3), board); // over top
    assertEquals(errMsgExpected, errMsgActual);
    errMsgActual = checker.checkMove(new Square(-1, 3), board); // over bottom
    assertEquals(errMsgExpected, errMsgActual);
    
  }
}
