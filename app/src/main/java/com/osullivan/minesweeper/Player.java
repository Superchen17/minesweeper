package com.osullivan.minesweeper;

import java.io.IOException;

public interface Player {
  public boolean isPlaying();
  public void makeOneMove() throws IOException;
}
