// benchmark that reverses a 100-element list 10000 times
// outputs tail average time
// expected output: mybenchmark: <time>

function benchmark(){
	list = new();
	i = 0;
	while (i < 100) {
	    list[i] = 100 - i;
	    i = i + 1;
	}

    // now do some list reversing using bubble sort:DDD
    p = 0;
    while (p < getSize(list)) {
        swapped = false;
        q = 0;
        while (q < (getSize(list) - p - 1)) {
            if (list[q] > list[q + 1]) {
                temp = list[q];
                list[q] = list[q + 1];
                list[q + 1] = temp;
                swapped = true;
            }
            q = q + 1;
        }
        p = p + 1;
        if (swapped == false) {
            break;
        }
    }
    return;
}


function main(){
    iterations = 1000;
    measure_from = 800;
    name="Swap";

    time = 0;
    it = 0;

    while (it < iterations){
    	s = nanoTime();
    	benchmark();
    	e = nanoTime() - s;
    	if (it >= measure_from){
    		time = time + e;
    	}
    	it = it + 1;

    }

    avg = time / (iterations - measure_from);
    println(name + ": " + avg);
    return;

}