function foo() {  
  i = 0;  
  while (i < 100) {
    println(i);
    if (i >= 5) {
      break;
    }  
    i = i + 1;  
  }
  println("finished with i=" + i);
}  

function main() {
  foo();
}