function CombineArray(a) { 
  len = getSize(a);
  comb = a[0];
  i = 1;

  while (i < len) {
    comb = comb + " " + a[i];
    i = i + 1;
  }

  return comb;
}

function benchmark() {
  //
  // Takes an array of words and combines them into a singular string
  //
  a = new();
  i = 0;

  while (i < 2000) {
    a[i] = "Element " + i;
    i = i + 1;
  }
  CombineArray(a);
}  

function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 10000;
  MEASURE_FROM = 8000;
  NAME = "Combine Array of Strings";

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
