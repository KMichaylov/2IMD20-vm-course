// This test tests a simple generator use case for functions with the eval primitive

function generator(number){
    if (number == 1){
        return "function func(i) {return i * 2;}";
    }
}

function main() {
    i = 1;
    j = 1;
    functionlist = new();
    functionlist[i] = generator(i);
    eval("sl", functionlist[i]);
    j = func(j, j);
    println(j);
}