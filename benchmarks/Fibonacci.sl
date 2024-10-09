function fib(num) { 
  if (num < 1) {return 0;}
  n1 = 0;
  n2 = 1;
  i = 1;
  while (i < num) {
    next = n2 + n1;
    n1 = n2;
    n2 = next;
    i = i + 1;
  }
  return n2;
}

function benchmark() {
  fib(8384);
}  

function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 10000;
  MEASURE_FROM = 8000;
  NAME = "Fibonacci";

  //
  // harness
  //
  time = 0;
  it = 0;

  while (it < ITERATIONS) {
    s = nanoTime();
    benchmark();
    e = nanoTime() - s;
    if (it >= MEASURE_FROM) {
      time = time + e;
    }
    it = it + 1;
  }

  avg = time / (ITERATIONS - MEASURE_FROM);
  // Make sure you print the final result -- and no other things!
  println(NAME + ": " + avg);
}
