package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class SquareTest {
  @Test
  public void test_constructor_int_valid(){
    Square square1 = new Square(0, 1);
    assertEquals(0, square1.getRow());
    assertEquals(1, square1.getColumn());

    Square square2 = new Square(-5, -3);
    assertEquals(-5, square2.getRow());
    assertEquals(-3, square2.getColumn());
  }

  @Test
  public void test_equals_true(){
    Square square1 = new Square(1, 5);
    Square square2= new Square(1, 5);
    assertEquals(square1, square2);
    assertEquals(square2, square1);
  }

  @Test 
  public void test_equals_false(){
    Square square1 = new Square(1, 5);
    Square square2 = new Square(1, 1);
    Square square3 = new Square(5, 5);

    assertNotEquals(square1, square2);
    assertNotEquals(square1, square3);
    assertNotEquals(square1, 0);
    assertNotEquals(square1, "asdf");
  }

  @Test
  public void test_hashCode(){
    Square square1 = new Square(1, 5);
    Square square2 = new Square(1, 5);

    HashSet<Square> squares = new HashSet<>();
    squares.add(square1);
    assertTrue(squares.contains(square2));
  }

  @Test
  public void test_toString(){
    Square square1 = new Square(1, 5);
    assertEquals("(5, 1)", square1.toString());

    Square square2 = new Square(-1, -5);
    assertEquals("(-5, -1)", square2.toString());

    Square square3 = new Square(15, 20);
    assertEquals("(20, 15)", square3.toString());
  }
}
