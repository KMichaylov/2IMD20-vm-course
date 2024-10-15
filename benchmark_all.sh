#!/bin/bash

FOLDER="./benchmarks"

if [ -z "$1" ]; then
    echo "Usage: $0 <executable>"
    exit 1
fi

EXECUTABLE=$1

LOGFILE="execution_log_$(date +%Y%m%d_%H%M%S).log"

echo "=== Logging output in $LOGFILE ==="

for file in "$FOLDER"/*.sl; do
    if [ -f "$file" ]; then
        echo "running $file..." >> "$LOGFILE"
        start_time=$(date +%s)

        "$EXECUTABLE" "$file" >> "$LOGFILE"

        end_time=$(date +%s)
        elapsed_time=$(( end_time - start_time ))
        echo " --> $file elapsed time: $elapsed_time seconds"

        echo " $file elapsed time $elapsed_time seconds" >> "$LOGFILE"
        echo "----------------------------------------" >> "$LOGFILE"
    fi
done

echo "=== Final scores ==="
# grep -Eo "[a-zA-Z0-9_-]+: [0-9]+" "$LOGFILE"

grep -Eo "[a-zA-Z0-9_-]+: [0-9]+" "$LOGFILE" | awk '{name = $1; val= $2; printf "%-30s %s \n", name, val;}'
