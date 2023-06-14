package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class MineGeneratorTest {
  private void checkMinesInBoundary(HashSet<Square> mines, int width, int height){
    for(Square mine: mines){
      assertTrue(mine.getColumn() < width);
      assertTrue(mine.getColumn() >= 0);
      assertTrue(mine.getRow() < height);
      assertTrue(mine.getRow() >= 0);
    }
  }

  @Test
  public void test_constructor_invalid(){
    assertThrows(IllegalArgumentException.class, ()->new MineGenerator(-1, 2, 1));
    assertThrows(IllegalArgumentException.class, ()->new MineGenerator(2, -1, 1));
    assertThrows(IllegalArgumentException.class, ()->new MineGenerator(2, 0, 1));
    assertThrows(IllegalArgumentException.class, ()->new MineGenerator(0, 2, 1));
    assertThrows(IllegalArgumentException.class, ()->new MineGenerator(2, 5, -1));
    assertThrows(IllegalArgumentException.class, ()->new MineGenerator(2, 5, 11));
    assertThrows(IllegalArgumentException.class, ()->new MineGenerator(2, 5, 11, 5,1));
  }

  @Test
  public void test_generateMines_withoutSeeds(){
    int width = 10;
    int height = 10;
    MineGenerator generator = new MineGenerator(width, height, 10);
    HashSet<Square> mines = generator.generateMines();
    assertEquals(10, mines.size());
    this.checkMinesInBoundary(mines, width, height);
  }

  @Test
  public void test_generateMines_withSeeds(){
    int width = 10;
    int height = 10;
    MineGenerator generator = new MineGenerator(width, height, 10, 5, 1);
    HashSet<Square> mines = generator.generateMines();
    assertEquals(10, mines.size());
    this.checkMinesInBoundary(mines, width, height);
  }
}
