function divides(a,b){

    while( b >= a) {
        b = b - a;
    }

    return(b == 0);
}

function lcm(a,b) {
    
    ans = 1;
    while( ans <= a*b) {
        if(divides(a, ans) && divides(b,ans)) {
            return(ans);
        }
        ans = ans + 1;
    }
}

function main() {
  println(lcm(1,16));
  println(lcm(16,1));
  println(lcm(4,8));
  println(lcm(13,2));
  println(lcm(25,30));
}