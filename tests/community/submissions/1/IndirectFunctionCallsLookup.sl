function add(a, b) { return a + b; }
function sub(a, b) { return a - b; }
function mul(a, b) { return a * b; }

function main() {
  operations = new();
  operations["add"] = add;
  operations["sub"] = sub;
  operations["mul"] = mul;

  println(operations["add"](10, 5));
  println(operations["sub"](10, 5));
  println(operations["mul"](10, 5));
}
