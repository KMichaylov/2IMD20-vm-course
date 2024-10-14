
function foo(f, i) {
  i = i + 1;
  if(i < 5)
  {
    f(f, i);
    println(i);
  }
  return i;
}

function main() {
  foo(foo, 0);
}
