function main(){
  i = 0;
  a = new();

  while (i < 10) {
    a[i] = i*100000000000;
    i = i + 1;
  }

  println(sum_list(a));

}

function sum_list(lst) {
  counter = 0;
  sum = 0;
  i = getSize(lst);
  while (counter < i) {
    sum = sum + lst[counter];
    counter = counter + 1;
  }
  return sum;
}
