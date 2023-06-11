package com.osullivan.minesweeper;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;

public class TextPlayer extends BasicPlayer {
  private final BufferedReader inStream;

  public TextPlayer(Board board, PrintStream outStream, BufferedReader inStream) {
    super(board, outStream);
    this.inStream = inStream;
  }

  protected String readAction(String prompt, HashSet<String> allowedChoices) throws IOException{
    String choice = null;
    while(choice == null){
      this.outStream.print(prompt);
      String s = this.inStream.readLine();
      if(s == null){
        throw new EOFException();
      }
      s = s.toUpperCase();
      if(!allowedChoices.contains(s)){
        this.outStream.print("invalid choice, choose from " + allowedChoices.toString() + "\n");
      }
      else{
        choice = s;
      }
    }
    return choice;
  }

  protected Square readSquare(String prompt) throws IOException {
    Square square = null;
    while(square == null){
      this.outStream.print(prompt);
      String s = this.inStream.readLine();
      if(s == null){
        throw new EOFException();
      }
      try {
        square = new Square(s);
      } catch (Exception e) {
        this.outStream.print(e.getMessage() + "\n");
      }
    }
    return square;
  }

  protected void displayResult(boolean hasLost){
    BoardTextView boardView = new BoardTextView(this.board);
    this.isPlaying = false;
    this.outStream.print(boardView.display(hasLost) + "\n");
    this.outStream.print("you have " + (hasLost ? "lost" : "won") + "!\n");
  }

  @Override
  public void makeOneMove() throws IOException {
    BoardTextView boardView = new BoardTextView(board);

    if(this.board.hasSteppedOnMine()){
      this.displayResult(true);
      return;
    }
    if(this.board.allMinesFlaggedAndSquareStepped()){
      this.displayResult(false);
      return;
    }

    this.outStream.print(boardView.display(false) + "\n");
    HashSet<String> allowedChoices = new HashSet<>();
    allowedChoices.add("F");
    allowedChoices.add("S");
    allowedChoices.add("U");

    String action = this.readAction(
      "enter your action\n" + 
      "\"F\" to flag, \"S\" to step, \"U\" to unflag\n", 
      allowedChoices
    );
    Square square = this.readSquare("enter your square in the format \"row, column\"\n");
    String errMsg = null;

    switch (action) {
      case "F":
        errMsg = this.board.putFlagOn(square);
        break;
      case "S":
        errMsg = this.board.stepOn(square, true);
        break;
      case "U":
        errMsg = this.board.unflagFrom(square);
        break;
    }

    if(errMsg != null){
      this.outStream.print(errMsg + "\n");
      this.makeOneMove();
    }
  }
}
