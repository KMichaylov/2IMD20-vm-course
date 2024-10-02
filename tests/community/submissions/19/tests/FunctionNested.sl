function print_sum() {
  println(1 + 1);
}

function invoke(f) {
  f();
}

function run(func) {
  invoke(func);
}

function main() {
  run(print_sum);
}  
