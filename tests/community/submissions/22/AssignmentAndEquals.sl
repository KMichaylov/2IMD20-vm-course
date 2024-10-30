function main() {
  a = 0;
  b = 1;
  c = 2;
  d = a == b;
  e = d == (a = b);

  println("a " + a);
  println("b " + b);
  println("c " + c);
  println("d " + d);
  println("e " + e);
}
