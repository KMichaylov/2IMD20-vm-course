/**
 * This is a benchmark that would benefit greatly from very fast
 * JIT inlining of the hot code path!
 *
 * On my PC the first run of this test in a new
 * docker container takes ~20s and afterwards ~12s
 * against the reference implementation.
 */
function loop(base, operation, parameter, iterations) {
  result = base;
  i = 1;
  while (i < iterations) {
    result = operation(result, parameter());
    i = i + 1;
  }
  return result;
}

function benchmark() {
  defineFunction("function add(a, b) { return a + b; }");
  defineFunction("function getMagicNumber() { return 5; }");
  loop(1, add, getMagicNumber, 1000);
}  

function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 10000;
  MEASURE_FROM = 8000;
  NAME = "JIT Inline";

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
