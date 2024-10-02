function main() {
  obj1 = new();
  obj1.x = 2;

  obj2 = new();
  obj2.x = 2;

  println(obj1 == obj2);

  println(obj1 == obj1);

  println(obj1.x == obj2.x);

  obj2.x = 0;

  println(obj1.x == obj2.x);
}