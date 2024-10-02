


function main(){
     
     arr = new();
     arr[0] = 64;
     arr[1] = 25;
     arr[2] = 12;
     arr[3] = 22;

     len = getSize(arr);

   
   

     
    i = 0;

    while(i < len - 1){
        minIndex = i;
        j = i + 1;

        while (j < len) {
            if(arr[j] < arr[minIndex]){
                minIndex = j;
            }
            j = j + 1;

        }

        if(minIndex != i){
            temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
        i = i + 1;
    }

    


    n = 0;
    while(n < len){
        println(arr[n] + " ");
        n = n + 1;
    }


}

