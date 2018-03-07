void vegetation() {
  for (int y = 0; y < H; y+=3) {
    for (int x = 0; x < W; x+=3) {
      do {
        outOfBounds = false;
        try {
          map.terrain[(int)random(y-2, y+2)][(int)random(x-2, x+2)].state = State.Tree;
        } 
        catch (Exception e) {
          outOfBounds=true;
        }
      } while (outOfBounds);
    }
  }
}