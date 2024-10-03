function recursion_test(obj, n) {
  println("depth " + n);
  if (obj[n + 1] != null) {
    recursion_test(obj, n + 1);
  }
}

function main() {
  obj = new();
  i = 0;
  while (i < 20) {
    obj[i] = numeric_test;
    i = i + 1;
  }

  recursion_test(obj, 0);
}
