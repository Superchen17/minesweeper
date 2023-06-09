package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BoardTest {
  @Test
  public void test_getters(){
    Board board = new Board(5, 10);
    assertEquals(5, board.getWidth());
    assertEquals(10, board.getHeight());
  }

  @Test
  public void test_putTakeFlags(){
    Board board = new Board(5, 10);
    Square square = new Square(3, 4);

    // empty
    assertFalse(board.isSquareFlagged(square));

    // put flag
    assertNull(board.putFlagOn(square));
    assertTrue(board.isSquareFlagged(square));

    // put again
    assertEquals("invalid move: the square is flagged", board.putFlagOn(square));
    assertTrue(board.isSquareFlagged(square));

    // unflag
    assertNull(board.unflagFrom(square));
    assertFalse(board.isSquareFlagged(square));

    // unflag again
    assertEquals("invalid move: the square is not flagged", board.unflagFrom(square));

    // off boundary
    assertEquals("invalid move: out of board", board.putFlagOn(new Square(5, 5)));
    assertEquals("invalid move: out of board", board.unflagFrom(new Square(5, 5)));
  }

  @Test
  public void test_stepOn(){
    Board board = new Board(5, 10);
    Square square = new Square(3, 4);

    // not stepped
    assertFalse(board.isSquareStepped(square));

    // step
    assertNull(board.stepOn(square, false));
    assertTrue(board.isSquareStepped(square));

    // step again
    assertEquals("invalid move: the square has been stepped", board.stepOn(square, false));
    assertTrue(board.isSquareStepped(square));

    // off boundary
    assertEquals("invalid move: out of board", board.stepOn(new Square(5, 5), false));
}

  @Test
  public void test_isMined(){
    HashSet<Square> mines = new HashSet<>();
    mines.add(new Square(3, 4));
    Board board = new Board(5, 10, mines);

    assertFalse(board.isSquareMined(new Square(3, 3)));
    assertTrue(board.isSquareMined(new Square(3, 4)));
  }

  @Test
  public void test_countMineAround(){
    HashSet<Square> mines = new HashSet<>();
    mines.add(new Square(9, 8));
    mines.add(new Square(8, 9));
    mines.add(new Square(5, 3));
    mines.add(new Square(5, 4));
    mines.add(new Square(5, 5));
    mines.add(new Square(4, 3));
    mines.add(new Square(3, 3));
    mines.add(new Square(3, 4));
    mines.add(new Square(3, 5));
    mines.add(new Square(0, 2));
    mines.add(new Square(0, 3));

    Board board = new Board(10, 10, mines);
    assertEquals(7, board.countMineAround(new Square(4, 4)));
    assertEquals(2, board.countMineAround(new Square(9, 9)));
    assertEquals(1, board.countMineAround(new Square(1, 1)));
    assertEquals(1, board.countMineAround(new Square(0, 4)));
    assertEquals(3, board.countMineAround(new Square(2, 4)));
    assertEquals(0, board.countMineAround(new Square(2, 7)));
    assertEquals(0, board.countMineAround(new Square(0, 9)));
  }
}
