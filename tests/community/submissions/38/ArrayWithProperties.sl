function main() {
  a = new();
  i = 0;
  while (i < 5) {
    a[i] = i;
    i = i + 1;
  }
  a.name = "my number list";
  println(a.name);
  i = 0;
  while (i < 5) {
    println(a[i]);
    i = i + 1;
  }
  a.self = a;
  println(a.self.name);
  println(a.self.self.self.self.self.name);
}

// This unit test involves array object with properties.
// We assign properties to an array and try to read the values.
// At the same time, we create the self-reference of the object - "a.self = a".
// Later we try to access the self-reference property from multiple consecutive calling on the object,
// to examine the robustness of the VM.