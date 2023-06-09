package com.osullivan.minesweeper;

public class Square {
  private final int row;
  private final int column;

  public Square(int row, int column){
    this.row = row;
    this.column = column;
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
