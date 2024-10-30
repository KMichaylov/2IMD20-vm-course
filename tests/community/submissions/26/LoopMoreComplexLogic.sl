/* The following loop has if, else break and continue checks, to see if they are handled correctly. */

function moreComplexLoop(n) {
    i = 0;
    flag = true;
  while (i < n) {
    if (i == 10) {
        i = i + 3;
        continue;
    }
    if (flag == true) {
        flag = false;
        i = i + 1;
    }
    else {
        flag = true;
        i = i + 2;
    }
    if (i == 42){
    break;
    }
  }
  return i;
}

function main() {
  println(moreComplexLoop(50));
}