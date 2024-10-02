function main() {  
    a = 12;
    b = 3;
    defineFunction("function shapeshifter(a, b) { return a + b; }");
    println("Current output of shapeshifting function: " + shapeshifter(a, b));
    
    defineFunction("function shapeshifter(a, b) { return a * b; }");
    println("Current output of shapeshifting function: " + shapeshifter(a, b));

    defineFunction("function shapeshifter(a, b) { return a - b; }");
    println("Current output of shapeshifting function: " + shapeshifter(a, b));

    defineFunction("function shapeshifter(a, b) { return a / b; }");
    println("Current output of shapeshifting function: " + shapeshifter(a, b));

    defineFunction("function shapeshifter(a, b) { return a + b; }");
    println("Current output of shapeshifting function: " + shapeshifter(a, b));
}