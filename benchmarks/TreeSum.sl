// Build a tree by creating objects with left, right and val definitions. 
function buildTree(h, k, root) {
    totalNodes = power2(h + 1) - 1;

    if (k == totalNodes) {
        return root;
    } else {
        if (2 * k + 1 < totalNodes) {
            root.left = new();
            root.left.val = 2 * k + 1;
            buildTree(h, 2 * k + 1, root.left);
        }

        if (2 * k + 2 < totalNodes) {
            root.right = new();
            root.right.val = 2 * k + 2;
            buildTree(h, 2 * k + 2, root.right);
        }
        return root;
    }
}

// Traverse through the tree and sum the values of the nodes
function traverseAndSum(root, k, totalNodes) {
    if (k >= totalNodes) {
        return 0;
    }

    sum = root.val;

    if (2 * k + 1 < totalNodes) {
        sum = sum + traverseAndSum(root.left, 2 * k + 1, totalNodes);
    }

    if (2 * k + 2 < totalNodes) {
        sum =  sum + traverseAndSum(root.right, 2 * k + 2, totalNodes);
    }

    return sum;
}

// Extra recursive helper method
function power2(n) {
    if (n == 0) {
        return 1;
    } else {
        return 2 * power2(n - 1);
    }
}

function benchmark() {
    treeRoot = new();
    treeRoot.val = 0;
    height = 13;
    builtRoot = buildTree(height, 0, treeRoot);
    totalNodes = power2(height + 1) - 1;
    traverseAndSum(builtRoot, 0, totalNodes);
}

function main() {
  //
  // benchmark constants
  //
  ITERATIONS = 10000;
  MEASURE_FROM = 8000;
  NAME = "TreeSum";

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