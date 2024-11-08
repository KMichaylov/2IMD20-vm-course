#!/bin/bash

# Folder containing the benchmark files
folder="./benchmarks"
timeout_duration=30 # Timeout duration in seconds

# Check if the provided SL executable exists and is executable
if [ -e "$1" ] && [ -x "$1" ]; then
  sl="$1"
else
  echo "The file '$1' either does not exist or is not executable."
  exit 1
fi

ok=0
fail=0
timeout_count=0
iters=0

# Initialize log files
touch execution_times.log
echo "" > execution_times.log

touch fatal_errors.log
echo "" > fatal_errors.log

# Remove any leftover .tmp files
rm -f *.tmp

# Iterate over all .sl files in the folder
for file in $(find $folder -type f -name "*.sl"); do
  # Skip if it's not a regular file
  [ -f "$file" ] || continue
  file="${file%.*}"

  ((iters++))

  echo "[$iters]"
  echo "[INFO] running $file.sl ..."

  tmpfile=$(mktemp)

  # Measure the execution time using `time` and capture the output with a timeout
  { time timeout $timeout_duration $sl "$file.sl" &> "$tmpfile"; } 2> time_output.tmp
  exit_code=$?

  # Extract the real time from the `time` command output
  elapsed=$(grep real time_output.tmp | awk '{print $2}')

  # Check if the timeout command caused a non-zero exit
  if [ $exit_code -eq 124 ]; then
    echo "[TIMEOUT] $file exceeded the timeout of $timeout_duration seconds."
    echo "[TIMEOUT] $file" >> fatal_errors.log
    ((timeout_count++))
  elif [ $exit_code -eq 0 ]; then
    echo "[ OK ] $file executed successfully in $elapsed."
    echo "[ OK ] $file: $elapsed" >> execution_times.log
    ((ok++))
  else
    echo "[ERROR] $file failed to execute."
    echo "[ERROR] $file" >> fatal_errors.log
    ((fail++))
  fi

  # Clean up temporary files after each iteration
  rm -f "$tmpfile" time_output.tmp
done

echo "========== ALL DONE =========="
echo "  successful runs: ${ok}"
echo "  failed runs: ${fail}"
echo "  timed out runs: ${timeout_count}"
echo ""
