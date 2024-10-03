function main() {
  a = new();

  a.splice(1, 1);
  println(a[1]);

  println("array size: " + getSize(a));
}