function main() {
  a = new();
    
  a[1] = foo();
  println("-----");

  a[1];
  println("-----");

  a[bar()] = bar;
  println("-----");

  a[2]();
  println("-----");

  a[3] = call;
  println("-----");

  a[3];
  println("-----"); 
}

function foo() {
  println(1);
  return 1;
}

function bar() {
  println(2);
  return 2;
}

function call() {
  println(3);
  return 3;
}
