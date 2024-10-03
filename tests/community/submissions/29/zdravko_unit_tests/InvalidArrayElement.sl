function main() {  
  obj1 = new();
  obj2 = new();
  obj2.x = 1;
  obj1[obj2] = obj2.x;
  println(getSize(obj1[obj2]));
}