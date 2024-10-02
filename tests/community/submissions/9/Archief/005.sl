function on(oc) {
  oc.d = 66;
}

function nuts(b) {
  b = 41;
}

function deez(o) {
  o.a = 6;
}

function main() {
  o = new();
  o.a = 1;
  o.b = 4;
  o.c = new();
  o.c.d = 5;

  println(o.a);
  println(o.b);
  println(o.c.d);

  deez(o);
  nuts(o.b);
  on(o.c);

  println(o.a);
  println(o.b);
  println(o.c.d);
}
