function main() {  

  array = new();

  i = 10;
  while (i > 0) {
    i = i - 1;
    array[i] = new();
    array[i].x = i;
  }
  
  j = 10;
  while (j > 0) {
    j = j - 1;
    println(array[j].x);
  }
}
