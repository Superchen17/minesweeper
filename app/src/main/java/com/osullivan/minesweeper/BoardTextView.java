package com.osullivan.minesweeper;

public class BoardTextView {
  private Board board;
  private SquareTextView squareTextView;

  public BoardTextView(Board board){
    this.board = board;
    this.squareTextView = new SquareTextView(' ', 'F', 'M', 'N');
  }

  public BoardTextView(Board board, SquareTextView squareTextView){
    this.board = board;
    this.squareTextView = squareTextView;
  } 

  protected int getRowPaddingLength(){
    return (int)Math.ceil(Math.log10(this.board.getHeight()));
  }

  protected int getColumnDisplayThickness(){
    return (int)Math.ceil(Math.log10(this.board.getWidth()));
  }

  protected String displayHorizontalLine(){
    StringBuilder lineView = new StringBuilder("");
    for(int i = 0; i < this.getRowPaddingLength(); i++){
      lineView.append(" ");
    }
    for(int i = 0; i < 2 * this.board.getWidth() + 1; i++){
      lineView.append("-");
    }
    return lineView.toString();
  }

  protected String displayRowNumber(int rowNumber){
    StringBuilder rowNumberView = new StringBuilder("");
    rowNumberView.append(
      String.format("%0"+this.getRowPaddingLength()+"d", rowNumber)
    );
    return rowNumberView.toString();
  }

  protected String displayColumnHeader(){
    StringBuilder columnNumberView = new StringBuilder("");
    for(int i = 0; i < this.getColumnDisplayThickness(); i++){
      for(int k = 0; k < this.getRowPaddingLength(); k++){
        columnNumberView.append(" ");
      }
      for(int j = 0; j < this.board.getWidth(); j++){
        columnNumberView.append(" ");
        int digitToDisplay = (j / (int)(Math.pow(10, i))) % 10;
        columnNumberView.append(Integer.toString(digitToDisplay));
      }
      if(i < this.getColumnDisplayThickness() - 1){
        columnNumberView.append("\n");
      }
    }
    return columnNumberView.toString();
  }

  public String display(boolean hasLost){
    StringBuilder boardView = new StringBuilder("");
    boardView.append(this.displayColumnHeader() + "\n");
    for(int i = this.board.getHeight() - 1; i >= 0; i--){
      boardView.append(this.displayHorizontalLine() + "\n");
      boardView.append(this.displayRowNumber(i));
      for(int j = 0; j < this.board.getWidth(); j++){
        boardView.append("|");
        boardView.append(this.squareTextView.display(new Square(i, j), this.board, hasLost));
      }
      boardView.append("|");
      boardView.append(this.displayRowNumber(i) + "\n");
    }
    boardView.append(this.displayHorizontalLine() + "\n");
    boardView.append(this.displayColumnHeader());
    return boardView.toString();
  }
}
