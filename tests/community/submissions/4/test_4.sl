// This test tests cyclic references

function main() {
    x = new();
    y = new();

    x.obj = y;
    y.obj = x;

    x.obj = y;

    x.obj.obj.message = "hello world";

    if (x.message != "hello world") {
        println("uh oh, broken reference!");
    }
    else {
        println(x.message);
    }
}