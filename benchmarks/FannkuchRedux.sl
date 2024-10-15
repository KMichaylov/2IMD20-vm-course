/*
 * Based on the script defined in the Computer Language Benchmarks Game
 * https://benchmarksgame-team.pages.debian.net/benchmarksgame/index.html
 */

function fannkuch(n) {
    perm1 = new();
    
    // For-loop (i = 0; i < n; i++)
    i = 0;
    while (i < n) {
        perm1[i] = i;
        i = i + 1;
    }

    perm = new();
    count = new();

    f = 0;
    flips = 0;
    nperm = 0;
    checksum = 0;

    r = n;
    while(r > 0) {
        i = 0;
        while (r != 1) {
            count[r - 1] = r;
            r = r - 1;
        }
        while (i < n) {
            perm[i] = perm1[i];
            i = i + 1;
        }

        // Count flips and update max and checksum
        f = 0;
        k = perm[0];
        while (k != 0) {
            i = 0;
            while ((2 * i) < k) {
                t = perm[i];
                perm[i] = perm[k - i];
                perm[k - i] = t;
                i = i + 1;
            }
            k = perm[0];
            f = f + 1;
        }

        if (f > flips) {
            flips = f;
        }

        if (isEven(nperm)) {
            checksum = checksum + f;
        } else {
            checksum = checksum - f;
        }

        // Use incremental change to generate another permutation
        more = true;
        while (more) {
            if (r == n) {
                return flips;
            }
            p0 = perm1[0];
            i = 0;
            while (i < r) {
                j = i + 1;
                perm1[i] = perm1[j];
                i = j;
            }
            perm1[r] = p0;

            count[r] = count[r] - 1;
            if (count[r] > 0) {
                more = false;
            } else {
                r = r + 1;
            }
        }
        nperm = nperm + 1;
    }
    return flips;
}

// Check if even by dividing by 2, then multiplying by 2 and checking if the result is the same as the original number
function isEven(n) {
    return ((n / 2) * 2) == n;
}

function benchmark() {
    fannkuch(6);
}

function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 10000;
  MEASURE_FROM = 8000;
  NAME = "FannkuchRedux";

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