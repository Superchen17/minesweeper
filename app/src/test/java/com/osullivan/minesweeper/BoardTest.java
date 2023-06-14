package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BoardTest {
  @Test
  public void test_constructor_default(){
    int width = 5;
    int height = 10;
    int expectedMines = 10;
    Board board = new Board(width, height, expectedMines);

    int mineCounter = 0;
    for(int i = 0; i < board.getHeight(); i++){
      for(int j = 0; j < board.getWidth(); j++){
        if(board.isSquareMined(new Square(i, j))){
          mineCounter++;
        }
      }
    }
    assertEquals(expectedMines, mineCounter);
  }

  @Test
  public void test_constructor_seeded(){
    int width = 10;
    int height = 10;
    MineGenerator generator = new MineGenerator(width, height, 10, 5, 1);
    HashSet<Square> mines = generator.generateMines();

    Board board = new Board(width, height, mines);
    BoardTextView boardView = new BoardTextView(board);
    String expectedView = 
    "  0 1 2 3 4 5 6 7 8 9\n" + 
    " ---------------------\n" +
    "9| |M| | | | | | | | |9\n" +
    " ---------------------\n" +
    "8| |M|M| | | | | | | |8\n" +
    " ---------------------\n" +
    "7| | | | |M| | | | | |7\n" +
    " ---------------------\n" +
    "6| |M| | | | | | | | |6\n" +
    " ---------------------\n" +
    "5| | | | | | | |M| | |5\n" +
    " ---------------------\n" +
    "4| | | | |M|M|M| | | |4\n" +
    " ---------------------\n" +
    "3| | | | |M| | | | | |3\n" +
    " ---------------------\n" +
    "2| | | | | | | | | | |2\n" +
    " ---------------------\n" +
    "1| | | | | | | | | | |1\n" +
    " ---------------------\n" +
    "0| | | | | | | | | | |0\n" +
    " ---------------------\n" +
    "  0 1 2 3 4 5 6 7 8 9";
    assertEquals(expectedView, boardView.display(true));
  }

  @Test
  public void test_constructor_invalid(){
    assertThrows(IllegalArgumentException.class, ()->new Board(-1, 2));
    assertThrows(IllegalArgumentException.class, ()->new Board(2, -1));
    assertThrows(IllegalArgumentException.class, ()->new Board(0, 5));
    assertThrows(IllegalArgumentException.class, ()->new Board(5, 0));
    assertThrows(IllegalArgumentException.class, ()->new Board(4,5,-2));
    assertThrows(IllegalArgumentException.class, ()->new Board(4,5,21));

    MineGenerator generator = new MineGenerator(3, 5, 15);
    assertThrows(IllegalArgumentException.class, ()->new Board(2,5,generator.generateMines()));
  }

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
  public void test_stepOn_noPropagation(){
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

  @Test
  public void test_stepOn_withPropagation(){
    HashSet<Square> mines = new HashSet<>();
    mines.add(new Square(1, 1));
    mines.add(new Square(2, 2));
    mines.add(new Square(3, 2));
    Board board = new Board(5, 5, mines);

    assertNull(board.stepOn(new Square(0, 4), true));

    BoardTextView boardView = new BoardTextView(board);
    String expectedBoardView;
    expectedBoardView = 
      "  0 1 2 3 4\n" +
      " -----------\n" + 
      "4| | | |1|0|4\n" +
      " -----------\n" +
      "3| | | |2|0|3\n" +
      " -----------\n" +
      "2| | | |2|0|2\n" +
      " -----------\n" +
      "1| | |2|1|0|1\n" +
      " -----------\n" +
      "0| | |1|0|0|0\n" +
      " -----------\n" +
      "  0 1 2 3 4";
    assertEquals(expectedBoardView, boardView.display(false));

    assertNull(board.putFlagOn(new Square(3, 0)));
    assertNull(board.stepOn(new Square(4, 0), true));
    expectedBoardView = 
      "  0 1 2 3 4\n" +
      " -----------\n" + 
      "4|0|1| |1|0|4\n" +
      " -----------\n" +
      "3|F|2| |2|0|3\n" +
      " -----------\n" +
      "2| | | |2|0|2\n" +
      " -----------\n" +
      "1| | |2|1|0|1\n" +
      " -----------\n" +
      "0| | |1|0|0|0\n" +
      " -----------\n" +
      "  0 1 2 3 4";
    assertEquals(expectedBoardView, boardView.display(false));
  }

  @Test
  public void test_hasSteppedOnMine(){
    HashSet<Square> mines = new HashSet<>();
    mines.add(new Square(1,1));
    Board board = new Board(5, 5, mines);

    assertFalse(board.hasSteppedOnMine());
    assertNull(board.stepOn(new Square(4, 4), true));
    assertFalse(board.hasSteppedOnMine());
    assertNull(board.stepOn(new Square(1, 1), true));
    assertTrue(board.hasSteppedOnMine());
  }

  @Test
  public void test_allMinesFlaggedAndSquareStepped(){
    HashSet<Square> mines = new HashSet<>();
    mines.add(new Square(1,1));
    Board board = new Board(5, 5, mines);

    // S(4,4) -> S(0,1) -> S(0,0) -> F(1,0) -> F(1,1) -> U(1,0)
    assertNull(board.stepOn(new Square(4, 4), true));
    assertFalse(board.allMinesFlaggedAndSquareStepped());
    assertNull(board.stepOn(new Square(0, 1), true));
    assertNull(board.stepOn(new Square(0, 0), true));
    assertNull(board.putFlagOn(new Square(1, 0)));
    assertFalse(board.allMinesFlaggedAndSquareStepped());
    assertNull(board.putFlagOn(new Square(1, 1)));
    assertFalse(board.allMinesFlaggedAndSquareStepped());
    assertNull(board.unflagFrom(new Square(1, 0)));
    assertFalse(board.allMinesFlaggedAndSquareStepped());
    assertNull(board.stepOn(new Square(1, 0), true));
    assertTrue(board.allMinesFlaggedAndSquareStepped());

  }
}
