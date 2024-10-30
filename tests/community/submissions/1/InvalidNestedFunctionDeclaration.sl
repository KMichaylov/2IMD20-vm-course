function outer() {
  function inner() {
    return 10;
  }
  return inner();
}

function main() {
  println(outer());
}