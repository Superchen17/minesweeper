package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class SquareTextViewTest {
  @Test
  public void test_display(){
    HashSet<Square> mines = new HashSet<>();
    mines.add(new Square(2, 1));
    mines.add(new Square(3, 2));
    Board board = new Board(5, 5, mines);

    assertNull(board.stepOn(new Square(2, 2), false));
    assertNull(board.putFlagOn(new Square(2, 1)));
    assertNull(board.putFlagOn(new Square(1, 3)));

    SquareTextView squareView = new SquareTextView(' ', 'F', 'M', 'N');
    assertEquals("2", squareView.display(new Square(2, 2), board, false));
    assertEquals("F", squareView.display(new Square(2, 1), board, false));
    assertEquals(" ", squareView.display(new Square(3, 2), board, false));
    assertEquals("F", squareView.display(new Square(1, 3), board, false));
    assertEquals(" ", squareView.display(new Square(3, 3), board, false));

    assertEquals(" ", squareView.display(new Square(2, 2), board, true));
    assertEquals("F", squareView.display(new Square(2, 1), board, true));
    assertEquals("M", squareView.display(new Square(3, 2), board, true));
    assertEquals("N", squareView.display(new Square(1, 3), board, true));
    assertEquals(" ", squareView.display(new Square(3, 3), board, true));
  }
}
