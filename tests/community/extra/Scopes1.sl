/* What is supposed to happen here? I get a strange exception. */

function main() {
    obj1 = new();
    if(1 == 1) {
        obj2 = new();
        obj2.x = "test";
        obj2.y = 42;
        obj1.o = obj2;

        println("Object 2:");
        println(obj2.x + obj2.y);
    }

    println("Object 1:");
    println(obj1.o.x + obj1.o.y);

    println("Object 2:");
    println(typeOf(obj2));
}
