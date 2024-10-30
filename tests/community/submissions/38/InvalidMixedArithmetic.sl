function main() {
  println(36 - 12 + "brandon");
  println("brandon" + 36 / 12);
  println("brandon" + (36 - 12));
  println("brandon" + 36 - 12);
}

// The VM should handle the expressions following such rules:
// 1. Brackets are always the first, then multiplications/divisions,
//    then we solve the expressions from left to right.
// 2. Until a string is involved, all calculations are based on numbers.
// 3. When a we have a string and a number to be added, we always connect their literals.
// 4. Any non-addition calculation between a string and a number is invalid.

// This unit test involves subtraction and division over strings and numbers.
// The error should occur when we try to perform subtraction on a string and a number.