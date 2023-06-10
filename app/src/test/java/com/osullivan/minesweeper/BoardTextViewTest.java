package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private BoardTextView getViewBySize(int width, int height){
    Board board = new Board(width, height);
    BoardTextView boardView = new BoardTextView(board);
    return boardView;
  }

  private Board createSimpleBoard(){
    HashSet<Square> mines = new HashSet<>();
    mines.add(new Square(2, 1));
    mines.add(new Square(3, 2));
    Board board = new Board(5, 5, mines);

    assertNull(board.stepOn(new Square(2, 2), false));
    assertNull(board.putFlagOn(new Square(2, 1)));
    assertNull(board.putFlagOn(new Square(1, 3)));
    return board;
  }

  @Test
  public void test_getRowPaddingLength(){
    BoardTextView boardView;
    boardView = this.getViewBySize(5, 10);
    assertEquals(1, boardView.getRowPaddingLength());
    boardView = this.getViewBySize(5, 11);
    assertEquals(2, boardView.getRowPaddingLength());
    boardView = this.getViewBySize(5, 100);
    assertEquals(2, boardView.getRowPaddingLength());
    boardView = this.getViewBySize(5, 101);
    assertEquals(3, boardView.getRowPaddingLength());
  }

  @Test
  public void test_displayHorizontalLine(){
    BoardTextView boardView;
    boardView = this.getViewBySize(5, 10);
    assertEquals(" -----------", boardView.displayHorizontalLine());
    boardView = this.getViewBySize(4, 11);
    assertEquals("  ---------", boardView.displayHorizontalLine());
    boardView = this.getViewBySize(4, 101);
    assertEquals("   ---------", boardView.displayHorizontalLine());
  }

  @Test
  public void test_displayRowNumber(){
    BoardTextView boardView;
    boardView = this.getViewBySize(5, 10);
    assertEquals("3", boardView.displayRowNumber(3));
    boardView = this.getViewBySize(5, 11);
    assertEquals("03", boardView.displayRowNumber(3));
    boardView = this.getViewBySize(5, 100);
    assertEquals("03", boardView.displayRowNumber(3));
    boardView = this.getViewBySize(5, 101);
    assertEquals("003", boardView.displayRowNumber(3));
  }

  @Test
  public void test_displayColumnNumber(){
    BoardTextView boardView;
    boardView = this.getViewBySize(5, 10);
    assertEquals("  0 1 2 3 4", boardView.displayColumnHeader());
    boardView = this.getViewBySize(12, 11);
    assertEquals(
      "   0 1 2 3 4 5 6 7 8 9 0 1\n"+
      "   0 0 0 0 0 0 0 0 0 0 1 1", 
      boardView.displayColumnHeader()
    );
  }

  @Test
  public void test_displayBoard_12x12(){
    BoardTextView boardView;
    boardView = this.getViewBySize(12, 12);

    String expectedView = 
      "   0 1 2 3 4 5 6 7 8 9 0 1\n" +
      "   0 0 0 0 0 0 0 0 0 0 1 1\n" +
      "  -------------------------\n" +
      "11| | | | | | | | | | | | |11\n" +
      "  -------------------------\n" +
      "10| | | | | | | | | | | | |10\n" +
      "  -------------------------\n" +
      "09| | | | | | | | | | | | |09\n" +
      "  -------------------------\n" +
      "08| | | | | | | | | | | | |08\n" +
      "  -------------------------\n" +
      "07| | | | | | | | | | | | |07\n" +
      "  -------------------------\n" +
      "06| | | | | | | | | | | | |06\n" +
      "  -------------------------\n" +
      "05| | | | | | | | | | | | |05\n" +
      "  -------------------------\n" +
      "04| | | | | | | | | | | | |04\n" +
      "  -------------------------\n" +
      "03| | | | | | | | | | | | |03\n" +
      "  -------------------------\n" +
      "02| | | | | | | | | | | | |02\n" +
      "  -------------------------\n" +
      "01| | | | | | | | | | | | |01\n" +
      "  -------------------------\n" +
      "00| | | | | | | | | | | | |00\n" +
      "  -------------------------\n" +
      "   0 1 2 3 4 5 6 7 8 9 0 1\n" +
      "   0 0 0 0 0 0 0 0 0 0 1 1";

    assertEquals(expectedView, boardView.display(false));
  }

  @Test
  public void test_displayBoard_everything(){
    Board board = this.createSimpleBoard();
    BoardTextView boardView = new BoardTextView(board);

    String expectedPlayingView = 
      "  0 1 2 3 4\n" +
      " -----------\n" +
      "4| | | | | |4\n" +
      " -----------\n" +
      "3| | | | | |3\n" +
      " -----------\n" +
      "2| |F|2| | |2\n" +
      " -----------\n" +
      "1| | | |F| |1\n" +
      " -----------\n" +
      "0| | | | | |0\n" +
      " -----------\n" +
      "  0 1 2 3 4";
    assertEquals(expectedPlayingView, boardView.display(false));

    String expectedLostView = 
      "  0 1 2 3 4\n" +
      " -----------\n" +
      "4| | | | | |4\n" +
      " -----------\n" +
      "3| | |M| | |3\n" +
      " -----------\n" +
      "2| |F| | | |2\n" +
      " -----------\n" +
      "1| | | |N| |1\n" +
      " -----------\n" +
      "0| | | | | |0\n" +
      " -----------\n" +
      "  0 1 2 3 4";
    assertEquals(expectedLostView, boardView.display(true));
  }
}
