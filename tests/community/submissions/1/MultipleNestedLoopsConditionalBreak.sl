function main() {
  i = 0;
  while (i < 5) {
    j = 0;
    while (j < 5) {
      if (i == 2 && j == 3) {
        break;
      }
      println("i: " + i + ", j: " + j);
      j = j + 1;
    }
    i = i + 1;
  }
}
