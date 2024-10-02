function main() {
  a = 0;
  b = "";
  while (a < 100) {
    if (a < 20) {
      b = b + "Deez";
    }
    if (a > 20 && a < 50) {
      b = b + "Nuts";
    }
    b = b + "";
    a = a + 1;
  }
  println(a);
  println(b);
}

