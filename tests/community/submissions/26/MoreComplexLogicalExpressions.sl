function main(){
    a = 10 == 10;
    b = 11 == 10;
    c = 10 == 10;
    d = 10 == 10;
    e = 9 == 10;
    logicalExpression = ((returnTrue(a) && returnFalse(b)) || returnTrue(c)) && returnTrue(d) || returnFalse(e);
    println(logicalExpression);
}

function returnTrue(x){
  return x;
}

function returnFalse(x){
  return x;
}
