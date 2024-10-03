function numeric_test(n) {
  println("number " + n);
}

function letter_test(n) {
  println("letter " + n);
}

function main() {
  obj = new();
  i = 0;
  while (i < 20) {
    obj[i] = numeric_test;
    i = i + 1;
  }

  obj["a"] = letter_test;
  obj["b"] = letter_test;
  obj["c"] = letter_test;

  i = 0;
  while(i < 20) {
    obj[i](i);
    i = i + 1;
  }

  obj["a"]("a");
  obj["b"]("b");
  obj["c"]("c");
}
