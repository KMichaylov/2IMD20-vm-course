function a(num){
  return num + 2;
}

function s(num){
  return 123;
}

function d(num){
  return num * 2;
}

function f(num){
  return d(num) + s(num);
}

function main(){
  println(f(31) + a(32));
}