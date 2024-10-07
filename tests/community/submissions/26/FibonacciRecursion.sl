function fibonacci(num) {
  if (num <= 1) {return num;}
  return fibonacci(num - 1) + fibonacci(num - 2);
}

function main() {
    n = 5;
    answer = fibonacci(n);
    println(answer);
}

