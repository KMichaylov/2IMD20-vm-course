function startSquareTillLargerThen10000(num) { 
    squareTillLargerThen10000(num, 1);
}

function squareTillLargerThen10000(num, depth) { 
    if (num > 10000) {
        println("Result: " + num + ", depth: " + depth);
    } else {
        squareTillLargerThen10000(num * num, depth + 1);
    }
}

function main() {  
    startSquareTillLargerThen10000(2);
    startSquareTillLargerThen10000(3);
    startSquareTillLargerThen10000(4);
    startSquareTillLargerThen10000(5);
    startSquareTillLargerThen10000(6);
    startSquareTillLargerThen10000(7);
    startSquareTillLargerThen10000(8);
    startSquareTillLargerThen10000(9);
    startSquareTillLargerThen10000(10);
}  

