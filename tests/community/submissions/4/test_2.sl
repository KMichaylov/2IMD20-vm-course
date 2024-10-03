// This test tests a simple generator use case for functions with the eval primitive

function generator(number){
    if (number == 0){
        return "function func(i) { return i + 10; }";
    }
    if (number == 1){
        return "function func(i) {return i * 2;}";
    }
    if (number == 2){
        return "function func(i, j) {return i + j;}";
    }
    if (number == 3){
        return "function func(i) {return (0 - i);}";
    }
}

function main() {
    i = 0;
    j = 0;
    functionlist = new();

    while (i < 4) {
        functionlist[i] = generator(i);
        i = i + 1;
    }

    i = 0;
    while (i < 4) {
        eval("sl", functionlist[i]);
        j = func(j, j);
        i = i + 1;
    }

    println(j);
}