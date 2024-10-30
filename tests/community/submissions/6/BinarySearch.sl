function main() {
     
     arr = new();
     arr[0] = 11;
     arr[1] = 22;
     arr[2] = 23;
     arr[3] = 25;
     arr[4] = 55;


     target = 25;

     left = 0;
     right = getSize(arr) - 1;

     while(left <= right){
        mid = (left + right) / 2;
        
        if(arr[mid] == target){
            println(mid);
            return;
        }

        if(target < arr[mid]){
            right = mid - 1;
        }else{
            left = mid + 1;
        }
     }


     return 0 - 1;

}
