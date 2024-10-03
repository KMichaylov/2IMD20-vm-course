function main() {
  a = 0;
  b = 0;
  while (a != 200) {
    while (b != 100) {
      if (b == 1) {
        break;
      }
      b = b + 1;
      a = a + 150;
    }
    a = a + 1;
  }

  println(a);
  println(b);
}
