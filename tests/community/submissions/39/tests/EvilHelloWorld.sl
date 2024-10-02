function main() {
    defineFunction("function main(str) { println(str); }");
    main("Hello world!");

    eval("sl", "function eval(str) { println(str); }");
    eval("Hello world!");

    defineFunction("function defineFunction(str) { println(str); }");
    defineFunction("Hello world!");
}
