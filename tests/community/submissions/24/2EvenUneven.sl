function isEven(num) { 
    if (num == 1 || num == 3 || num == 5 || num == 7 || num == 9) {return false;}
    return true;
}

function main() {  
    i = 1;
    while (i <= 10) {
        if (isEven(i) == true) {
            println(i + ": even");
        }
        else {
            println(i + ": uneven");
        }
    i = i + 1;
  }
}  

