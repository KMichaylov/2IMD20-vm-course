function main() {
    obj1 = "test";
    obj2 = new();
    obj2["obj1"] = obj1;


    println(obj1);
    println(obj2["obj1"]);

    obj1 = "test2";

    println(obj1);
    println(obj2["obj1"]);
    
}