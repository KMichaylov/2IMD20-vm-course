function tail_factorial(n, acc) {
  if (n == 0) {
    return acc;
  }
  else {
    return tail_factorial(n - 1, n * acc);
  }
}

function main() {
  println(tail_factorial(5,1));
}
