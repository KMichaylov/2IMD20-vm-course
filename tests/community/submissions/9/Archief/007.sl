function print(a) {
  b = 0;
  n = getSize(a);
  while (b < n) {
    println(a[b]);
    b = b + 1;
  }
}

function asdf(a) {
  b = 0;
  while (b < 11) {
    a[b] = b * 2;
    b = b + 1;
  }
}

function main() {
  a = new();
  asdf(a);
  print(a);
}
