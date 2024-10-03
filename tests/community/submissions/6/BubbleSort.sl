function main() {
     
     arr = new();
     arr[0] = 64;
     arr[1] = 25;
     arr[2] = 12;
     arr[3] = 22;

     len = getSize(arr);

    swapped = 1;

    while(swapped == 1){
        swapped = 0;
        i = 0;

        while(i < len -1){
            if(arr[i] > arr[i + 1]){
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
                swapped = 1;
            }
            i = i + 1;
        }

        len = len - 1;
    }

 



    n = 0;
    while(n < getSize(arr)){
        println(arr[n]);
        n = n + 1;
    }


}
