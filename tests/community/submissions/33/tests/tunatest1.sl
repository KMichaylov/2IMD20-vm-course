
function isitodd(num) { 
  if ((num / 2) == ((num-1) / 2)) {return 1;}

  return 0;
}

function main() {  
  i = 1;
  while (i <= 5) {
    if (isitodd(i) == 1) {println(i + " is odd");}
    else {println(i + " is even");}
    i = i + 1;
  }
}