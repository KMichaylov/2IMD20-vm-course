function main() {
    obj = new();
    obj["x"] = 42;
    obj["y"] = new();
    obj["y"]["z"] = 30;
    println(obj);
    println(obj["x"]);
    println(obj["y"]);
    println(obj["y"]["z"]);

    obj["y"] = 20;
    println(obj["x"]);
    println(obj["y"]);

}