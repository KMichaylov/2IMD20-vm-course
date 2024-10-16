function stringGenerator(length, pattern, specialIndex, specialPattern) {
  s = "";
  i = 0;
  while (i < length) {
    if (i == specialIndex) {
      s = s + specialPattern;
    } else {
      s = s + pattern;
    }
    i = i + 1;
  }
  return s;
}

function substrSearch(text, pattern) {
  textLength = getSize(text);
  patternLength = getSize(pattern);
  
  i = 0;
  while(i <= textLength - patternLength) {
    if (subString(text, i, i + patternLength) == pattern) {
      return i;
    }
    i = i + 1;
  }
  
  return null;
}

function benchmark() {
  specialPattern = stringGenerator(50, "t", 40, "u");
  text = stringGenerator(5000, "t", 4500, specialPattern);
  substrSearch(text, specialPattern);
}  

function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 10000;
  MEASURE_FROM = 8000;
  NAME = "SubstringSearch";

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
