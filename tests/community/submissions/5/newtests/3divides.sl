function divides(a,b){

    while( b >= a) {
        b = b - a;
    }

    return(b == 0);
}

function main() {
  println(divides(1,16));
  println(divides(16,1));
  println(divides(4,8));
  println(divides(13,2));
  println(divides(25,30));
  println(divides(25,5));
  println(divides(5,8));
  println(divides(101,707));
}