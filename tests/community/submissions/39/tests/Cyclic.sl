function main() {
    obj1 = new();
    obj1.data = 42;

    obj2 = new();
    obj2.data = 1337;

    obj1.child = obj2;
    obj2.child = obj1;

    println(obj1.data);
    println(obj1.child.data);
    println(obj1.child.child.data);

    println("");

    println(obj2.data);
    println(obj2.child.data);
    println(obj2.child.child.data);

    println("");

    obj1.child.child.child.data = 1;
    obj2.child.child.child.data = 2;
    println(obj1.data);
    println(obj2.data);
}
