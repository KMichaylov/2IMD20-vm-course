function is_even(n) {
  if (n == 0) {
    return true;
  } else {
    return is_odd(n - 1);
  }
}

function is_odd(n) {
  if (n == 0) {
    return false;
  } else {
    return is_even(n - 1);
  }
}

function main() {
  println(is_odd(3)); 
  println(is_even(4));
}
