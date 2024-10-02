function adduntilsixty(num){
  if(num < 60) {return adduntilsixty(num+1);}
  return num;
}

function main(){
  println(adduntilsixty(1));
}