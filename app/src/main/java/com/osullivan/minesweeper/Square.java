package com.osullivan.minesweeper;

import java.util.Arrays;
import java.util.List;

public class Square {
  private final int row;
  private final int column;

  /**
   * constructor accepting integers
   * @param row
   * @param column
   */
  public Square(int row, int column){
    this.row = row;
    this.column = column;
  }

  /**
   * constructor accepting square defined as string
   * in the format of "row, column"
   * @param s <row, column>
   */
  public Square(String s){
    List<String> elements = Arrays.asList(s.replaceAll("\\s", "").split(","));
    String errMsg = "square must be in form of <row, column>";
    if(elements.size() != 2){
      throw new IllegalArgumentException(errMsg);
    }
    try {
      this.row = Integer.parseInt(elements.get(0));
      this.column = Integer.parseInt(elements.get(1));
    } catch (Exception e) {
      throw new IllegalArgumentException(errMsg);
    }
  }

  public int getRow(){
    return this.row;
  }

  public int getColumn(){
    return this.column;
  }

  @Override
  public boolean equals(Object o) {
    if(o.getClass().equals(this.getClass())){
      Square rhs = (Square)o;
      return this.row == rhs.row && this.column == rhs.column;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }

  @Override
  public String toString() {
    return "(" + Integer.toString(column) + ", " + Integer.toString(row) + ")";
  }
}
