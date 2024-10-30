function fA() {}
function fB() {}

function main() {
  fA();
  if (2 < 1) {
    fB();
    return 1;
  }
  return 2;

}