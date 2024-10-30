function main() {
  obj1 = new();
  obj1[1] = "x";
  obj2 = new();
  obj2[obj1] = "y";
  println(obj2[obj1]);
}
