function createMatrix(n) {
    matrix = new();
    value = 1;
    i = 0;
    
    while (i < n) {
        j = 0;
        matrix[i] = new();
        while(j < n) {
            
            matrix[i][j] = value;

            value = value + 1;
            j = j + 1;
            
        }
        i = i + 1;
    }

    return matrix;
}

function matMult(A, B) {
    n = getSize(A);

    result = new();

    i = 0;
    while(i < n) {
        j = 0;
        result[i] = new();
        
        while (j < n) {
            k = 0;
            result[i][j] = 0;
            while (k < n) {

                result[i][j] = result[i][j] + (A[i][k] * B[k][j]);
                k = k + 1;
                
            }
            j = j + 1;
        }
        i = i + 1;
    }
    return result;
}

function benchmark() {
   n = 20;
   A = createMatrix(n);
   B = createMatrix(n);

   result = matMult(A, B);
   //println("Done");
}  

function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 1000;
  MEASURE_FROM = 800;
  NAME = "MatMul";

  //
  // harness
  //
  time = 0;
  it = 0;

  while (it < ITERATIONS) {
    s = nanoTime();
    benchmark();
    e = nanoTime() - s;
    if (it >= MEASURE_FROM) {
      time = time + e;
    }
    it = it + 1;
  }

  avg = time / (ITERATIONS - MEASURE_FROM);
  // Make sure you print the final result -- and no other things!
  println(NAME + ": " + avg);
}
