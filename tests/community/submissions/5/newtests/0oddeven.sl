function EvenOdd(num) {

    while( num >= 2) {
        num = num - 2;
    }

    return(num == 0);
}

function main() {
  println(EvenOdd(1));
  println(EvenOdd(2));
  println(EvenOdd(3));
  println(EvenOdd(4));
}