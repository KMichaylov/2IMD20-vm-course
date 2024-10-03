function main() {
  i = 0;
  j = 0;
  k = 0;
  while (i < 100) {
    while  (j < 100) {
        while (k < 100) {
            k = k + 1;
        }
        j = j + 1;
    }
    i = i + 1;
  }
  println(i);
  println(j);
  println(k);
}
