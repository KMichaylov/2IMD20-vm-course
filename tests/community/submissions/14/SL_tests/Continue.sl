function main() {
  i = 0;
  while (i < 10) {
    if (i < 5) {
      println("in");
      i = i + 1;
      continue;
    }
    break;
  }
}
