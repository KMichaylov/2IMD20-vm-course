function next() {
  a = 150;
  b = 412;
  c = a + b;

  return a;
}

function main() {
  a = 1;
  b = 2;
  c = 3;
  println(a);
  println(b);
  println(c);

  c = next();

  println(a);
  println(b);
  println(c);
}
