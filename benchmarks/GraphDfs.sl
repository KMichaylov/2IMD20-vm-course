
function mod(a, b) {
    return a - (a / b) * b;
}

function createDirectedGraph(n) {
    result = new();
    i = 0;
    while (i < n) {
        adj = new();

        j = 0;
        increase = 0;
        while (j < n) {
            adj[getSize(adj)] = j;
            j = j + 1;
        }

        node = new();
        node.adj = adj;
        node.value = i;
        result[i] = node;
        i = i + 1;
    }

    return result;
}

function dfs(graph, visited, nodeIndex, depth) {
    if (visited[nodeIndex]) {
        return 0;
    }
    visited[nodeIndex] = (0 == 0);

    node = graph[nodeIndex];
    result = 0;
    i = 0;
    while (i < getSize(node.adj)) {
        result = result + dfs(graph, visited, node.adj[i], depth + 1);
        i = i + 1;
    }
    result = result + depth * node.value;
    return result;
}

function benchmark(n) {
    graph = createDirectedGraph(n);

    visited = new();
    i = 0;
    while (i < n) {
        visited[i] = (0 == 1);
        i = i + 1;
    }

    result = 0;
    i = 0;
    while (i < n) {
        result = result + dfs(graph, visited, i, 0);
        i = i + 1;
    }
    return result;
}

function main() {
    //
    // benchmark constants
    //
    ITERATIONS = 800;
    MEASURE_FROM = 600;
    NAME = "GraphDfs";

    //
    // harness
    //
    time = 0;
    it = 0;

    while (it < ITERATIONS) {
        s = nanoTime();
        benchmark(80);
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
