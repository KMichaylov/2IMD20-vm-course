function main() {
  obj = new();
  println(getSize(obj));

  obj[1] = "a";
  println(getSize(obj));

  obj["x"] = "b";
  println(getSize(obj));

  obj["y"] = "c";
  println(getSize(obj));

  obj["z"] = main;
  println(getSize(obj));

  obj["y"] = null;
  println(getSize(obj));
}
