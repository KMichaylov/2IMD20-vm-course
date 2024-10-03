function main() {
   initial = nanoTime();

   i = 0;
   while(i < 1000)
   {
    i = i + 1;
   }

   if(initial < nanoTime())
   {
       println("Time is increasing");
   }
}
