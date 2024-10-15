// benchmark does a lot of array manipulations, recursion and backtracking.

function nQueensSolver(n, printYes) {
    solutions = new();  
    board = createBoard(n); // "." = empty position, "Q" = queen placement

    solve(board, 0, n, solutions);

    if(printYes) {
        println("Number of sols: " + getSize(solutions));

        // print the first solution if it exists
        if(getSize(solutions) > 0) {
            solution = solutions[1];
            row = 0;
            while (row < getSize(solution)) {
                // no print, so we need to separate the different rows
                println("--- row: " + row + " ---");
                index = 0;
                while (index < getSize(solution[row])) {
                    println(solution[row][index]);
                    index = index + 1;
                } 
                row = row + 1;
            }
        }
    }
}

// creates empty n x n board
function createBoard(n) {
    board = new();
    i = 0;
    while(i < n) {
        row = new();
        j = 0;
        while(j < n) {
            row[j] = ".";
            j = j + 1;
        }
        board[i] = row;
        i = i + 1;
    }
    return board;
}

// checks if queen can be placed safely
function isSafe(board, row, col, n) {
    // check current column
    i = 0;
    while(i < row) {
        if(board[i][col] == "Q") {
            return 0;
        }
        i = i + 1;
    }

    // check upper-left diagonal
    i = row;
    j = col;
    while(i >= 0 && j >= 0) {
        if(board[i][j] == "Q") {
            return 0;
        }
        i = i - 1;
        j = j - 1;
    }

    // check upper-right diagonal
    i = row;
    j = col;
    while(i >= 0 && j < n) {
        if(board[i][j] == "Q") {
            return 0;
        }
        i = i - 1;
        j = j + 1;
    }

    return 1;
}

// solver for n-queens problem (recursion)
function solve(board, row, n, solutions) {
    if(row == n) {
        // copy current sol
        solution = new();
        i = 0;
        while(i < n) {
            solution[i] = new();
            j = 0;
            // seems like toy doesn't support just doing solution[i] = board[i]. The Q positions are lost doing that.
            while (j < getSize(board[i])) { 
                solution[i][j] = board[i][j];
                j = j + 1;
            }
            i = i + 1;
        }
        solutions[getSize(solutions)] = solution;
        return;
    }

    col = 0;
    while(col < n) {
        if(isSafe(board, row, col, n) == 1) {
            board[row][col] = "Q";  
            solve(board, row + 1, n, solutions);  
            board[row][col] = ".";  // back track step
        }
        col = col + 1;
    }
}

function benchmark() {
    // arg is size of board, 7 is within the 10-20 sec range (8 takes ~2 mins)
    // arg 2 is to print or not to print (printing disabled for the benchmark)
    nQueensSolver(7, false); 
}  

function main() {
    //
    // benchmark constants
    //
    ITERATIONS = 8000;
    MEASURE_FROM = 6000;
    NAME = "N-Queens";

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
