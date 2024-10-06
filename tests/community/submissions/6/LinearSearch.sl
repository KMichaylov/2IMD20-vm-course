function main() {
     
     arr = new();
     arr[0] = 81;
     arr[1] = 26;
     arr[2] = 23;
     arr[3] = 35;
     arr[4] = 5;


     target = 35;

     i = 0;

     while(i < getSize(arr)){
        if(arr[i] == target){
        println(i);
        return;
        }
        i = i + 1;
     }
     
     return 0 - 1;

}
