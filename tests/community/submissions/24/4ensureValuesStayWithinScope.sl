function editSameNameValue() { 
    privateValue = "imposter";
    println(privateValue);
}

function main() {  
    privateValue = "original";
    editSameNameValue();
    println(privateValue);
} 