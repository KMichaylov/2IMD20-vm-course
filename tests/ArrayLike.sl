function main() {  
  a = new();
  
  a[0] = 42;
  println(a[0]);
  println("array size: " + getSize(a));

  a[0] = "x";
  println(a[0]);
  println("array size: " + getSize(a));

  a[1] = true;
  println(a[1]);
  println("array size: " + getSize(a));

  a[12] = 43;
  println(a[1]);
  println("array size: " + getSize(a));


  i = 0;
  while (i < 42) {
    a[i] = "x@" + i;
    println("Element " + i + " -> " + a[i]);
    i = i + 1;
  }

  println("array size: " + getSize(a));
}
