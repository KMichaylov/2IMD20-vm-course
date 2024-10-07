function factorial(num) {

    if(num == 0) {
        return(1);
    }

    result = 1;

    while (num > 1) {
    result = result * num;
    num = num - 1;
    }
    return(result);
}

function main() {
  println(factorial(0));
  println(factorial(1));
  println(factorial(2));
  println(factorial(3));
  println(factorial(4));
  println(factorial(5));
  println(factorial(6));
}
