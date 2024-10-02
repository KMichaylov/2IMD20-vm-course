function main() {
  i = 0;  
  while (i < 5) {
    j = 0;
    while (j < 2) {
      if (i + j >= 4) {
        break;
      }
      println("i=" + i + " j=" + j);
      j = j + 1;
    }
    println("---");
    i = i + 1;
  }
  println("finished with i=" + i);
}
