function main() {
  obj1 = new();
  obj2 = new();
  
  obj1[0] = obj2;
  println(obj1[0]);
  obj2.x = 1;
  obj1[0] = obj2.x;
  println(obj1[0]);
}
