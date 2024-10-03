function main() {

    tree = create_child(
        create_child(
            create_child(1,2),
            create_child(
                create_child(3,4),
                create_child(5,6)
            )
        ),
        create_child(7,8)
    );

    pp(tree);
}

function create_child(left, right) {
    obj = new();
    obj["left"] = left;
    obj["right"] = right;
    return obj;
}

function pp(tree) {
    if (isInstance(typeOf(1), tree["left"])) {
        println(tree["left"]);
    } else {
        pp(tree["left"]);
    }
    if (isInstance(typeOf(1), tree["right"])) {
        println(tree["right"]);
    } else {
        pp(tree["right"]);
    }
}
