function stringBuilder(str, add) {
    return str + " " + add;
}

function main() {
    println(stringBuilder(stringBuilder("Hello", "There"), "World!"));
}