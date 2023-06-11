package com.osullivan.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  private TextPlayer createTextPlayer(int width, int height, String inputData, OutputStream outStream){
    Board board = new Board(width, height);
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(outStream, true);
    TextPlayer player = new TextPlayer(board, output, input);
    return player;
  }

  private HashSet<String> createAllowedChoices(){
    HashSet<String> allowedChoices = new HashSet<>();
    allowedChoices.add("F");
    allowedChoices.add("S");
    allowedChoices.add("U");
    return allowedChoices;
  }

  @Test
  public void test_readAction_valid() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = this.createTextPlayer(10, 10, "f\n", bytes);
    assertEquals("F", player.readAction("", this.createAllowedChoices()));
    bytes.reset();
  }

  @Test
  public void test_readAction_invalid() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = this.createTextPlayer(10, 10, "a\nf\n", bytes);
    HashSet<String> allowedChoices = this.createAllowedChoices();

    String prompt = "please enter your choice:\n";
    String errMsg = "invalid choice, choose from " + allowedChoices.toString() + "\n";

    assertEquals("F", player.readAction(prompt, allowedChoices));
    assertEquals(prompt + errMsg + prompt, bytes.toString());
    bytes.reset();
  }

  @Test
  public void test_readAction_null(){
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = this.createTextPlayer(10, 10, "\n", bytes);
    assertThrows(EOFException.class, ()->player.readAction("", this.createAllowedChoices()));
    bytes.reset();
  }

  @Test
  public void test_readSquare_valid() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = this.createTextPlayer(10, 10, "1,2\n", bytes);
    assertEquals(new Square(1,2), player.readSquare("prompt"));
    bytes.reset();
  }

  @Test
  public void test_readSquare_invalid() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = this.createTextPlayer(10, 10, "12\n1,2\n", bytes);
    String prompt = "enter your square:\n";
    String errMsg = "square must be in form of <row, column>\n";
    assertEquals(new Square(1,2), player.readSquare(prompt));
    assertEquals(prompt + errMsg + prompt, bytes.toString());
    bytes.reset();
  }

  @Test
  public void test_readChoicen_null(){
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = this.createTextPlayer(10, 10, "\n", bytes);
    assertThrows(EOFException.class, ()->player.readSquare(""));
    bytes.reset();
  }
}
