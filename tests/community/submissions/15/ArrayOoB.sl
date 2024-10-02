function arrOutOfBand(inputArr) {
  i = 0;
  while (i < 4) {
    println(inputArr[i]);
    i = i + 1;
  }
}

function main() {
  a = new();
  
  a[0] = "a1";
  a[1] = "a2";
  a[2] = "a3";
  arrOutOfBand(a);
}  