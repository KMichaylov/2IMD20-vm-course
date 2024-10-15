//width 4
// height 5

function convert2dTo1d(i, j)
{
    return i * 4 + j;
}

function count_live_neighbour_cell(values, r, c)
{
    i = r - 1;
    count = 0;

    while (i <= r + 1) {
        j = c - 1;
        while (j <= c + 1) {
            if ((i == r && j == c) || (i < 0 || j < 0)
                || (i >= 5 || j >= 4)) {
                j = j + 1;
                continue;
            }
            if (values[convert2dTo1d(i,j)] == 1) {
                count = count + 1;
            }
            j = j + 1;
        }
        i = i + 1;
    }
    return count;
}

function row_line()
{
    //println("");
    row = "";
    i = 0;
    while (i < 4) {
        row = row + "-------";
        i = i + 1;
    }
    //println(row);
}


function printMatrix(values)
{
    row_line();
    i = 0;
    while (i < 5) {
        row = ":";
        j = 0;
        while (j < 4) {
            row = row + "  " + values[convert2dTo1d(i,j)] + "  :";
            j = j + 1;
        }
        //println(row);
        row_line();
        i = i + 1;
    }
}

function benchmark()
{
    values = new();
    values[0] = 1;
    values[1] = 1;
    values[2] = 0;
    values[3] = 0;

    values[4] = 1;
    values[5] = 0;
    values[6] = 0;
    values[7] = 0;

    values[8] = 0;
    values[9] = 0;
    values[10] = 1;
    values[11] = 1;

    values[12] = 1;
    values[13] = 1;
    values[14] = 1;
    values[15] = 1;

    values[16] = 1;
    values[17] = 0;
    values[18] = 1;
    values[19] = 0;

    new_values = new();

    //println("Initial Matrix:");
    printMatrix(values);

    amount_of_runs = 50;
    current_run = 0;
    while(current_run < amount_of_runs)
    {
            i = 0;
            j = 0;
            neighbour_live_cell = 0;

            i = 0;
            while (i < 5) {
                j = 0;
                while (j < 4) {
                    neighbour_live_cell = count_live_neighbour_cell(values, i, j);
                    if (values[convert2dTo1d(i,j)] == 1 && (neighbour_live_cell == 2 || neighbour_live_cell == 3)) {
                        new_values[convert2dTo1d(i,j)] = 1;
                    } else {
                        if (values[convert2dTo1d(i,j)] == 0 && neighbour_live_cell == 3) {
                            new_values[convert2dTo1d(i,j)] = 1;
                        }
                        // Handle the underpopulation and overpopulation scenarios
                        else {
                            new_values[convert2dTo1d(i,j)] = 0;
                        }
                    }
                    j = j + 1;
                }
                i = i + 1;
            }
            //println("Matrix at iteration: " + current_run);
            printMatrix(new_values);
            values = new_values;
            current_run = current_run + 1;
    }
}

function main()
{
    ITERATIONS = 10000;
    MEASURE_FROM = 5000;
    NAME = "Game of Life";

    time = 0;
    it = 0;
    while(it < ITERATIONS) {
        s = nanoTime();
        benchmark();
        e = nanoTime() - s;
        if(it >= MEASURE_FROM)
        {
            time = time + e;
            //println(e);
        }        
        it = it + 1;
    }
    avg = time / (ITERATIONS - MEASURE_FROM);
    println(NAME + ": " + avg);
}