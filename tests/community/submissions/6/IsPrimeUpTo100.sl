function mod(num, divide) {

    return num - num/divide * divide;
}


function isPrime(num) {
    if(num < 2){
         return 0;}
    i = 2;
    while(i * i <= num){
        if(mod(num, i) == 0){
            
            return 0;
        
        }
        i = i + 1;
    }
    return 1;
}




function main(){
    primes = new();
    num = 2;
    i = 0;
    

    while(num <= 100){
        if(isPrime(num) == 1){
          primes[i] = num;
        i = i + 1;
          
        }
        num = num + 1;
    }

    len = getSize(primes);
    

    n = 0;
    while(n < len){
        println(primes[n]);
        n = n + 1;
    }


}