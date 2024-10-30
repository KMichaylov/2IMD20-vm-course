function foo(a, b) {
  println("I should never say anything!");
}

function man() {
  println("I actually am NOT main, hehe");
  foo();
}
