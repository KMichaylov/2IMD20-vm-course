function power(b,e) {
    /*
    *0^0 would be a problem, but for this toy language I don't want to create custom errors or anything of the kind
    */
    if(e == 0) {
        return(1);
    }



    result = 1;

    while (e > 0) {
    result = result * b;
    e = e - 1;
    }
    return(result);
}

function main() {
  println(power(1,16));
  println(power(2,5));
  println(power(4,0));
  println(power(13,2));
}