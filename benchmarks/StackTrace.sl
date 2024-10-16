function stackTrace(var) { 
  stTr1 = stacktrace();
  char = "a";
  num = 0;
  stTr2 = stacktrace();
  var[0] = 1;
  var[1] = "Buongiorno";
  elem = var[1];
  stTr3 = stacktrace();
  num = 50;
  i = 0;
  while (i < num) {
    var[i] = num - i;
    i = i + 1;
    stacktrace();
  }
  return var[4];
}

function benchmark() {
  stackTrace(new());
}  

function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 6000;
  MEASURE_FROM = 1200;
  NAME = "StackTracing";

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
