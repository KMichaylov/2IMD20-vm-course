function stringCon(limit) {
  str1 = "";
  str2 = "";
  str3 = "";
  i = 0;
  while (i < limit) {
    str1 = str1 + "a";
    str2 = str2 + "ab";
    str3 = str3 + "abc";
    i = i + 1;
  }
  answer = str1 + str2 + str3;
  return answer;
}

function benchmark() {
  stringCon(9000);
}

function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 18000;
  MEASURE_FROM = 14400;
  NAME = "StringConcat";

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