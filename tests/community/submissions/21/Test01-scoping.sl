/** 
    Test for scoping.
*/


function foo() {
    sameName = 100;
    return sameName;
}

function main() {   
    sameName = 5;
    foo();
    sameName = sameName + 6;
    println(sameName);
}