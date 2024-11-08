#!/bin/bash

# Folder containing the input files
folder="./tests"

# Check if the provided SL executable exists and is executable
if [ -e "$1" ] && [ -x "$1" ]; then
  sl="$1"
else
  echo "The file '$1' either does not exist or is not executable."
  exit 1
fi

ok=0
fail=0
fatals=0
iters=0
timeout_limit=5  # Timeout in seconds

# Initialize log files
touch fatal_errors.log
echo "" > fatal_errors.log

touch wrong_stderr.log
echo "" > wrong_stderr.log

touch wrong_stdout.log
echo "" > wrong_stdout.log

touch suspicious.log
echo "" > suspicious.log

# Remove any leftover .tmp files
rm -f *.tmp

# Iterate over all .sl files in the folder
for file in $(find "$folder" -type f -name "*.sl"); do
  # Skip if it's not a regular file
  [ -f "$file" ] || continue
  file="${file%.*}"

  ((iters++))

  # Define output and error files
  output_file="${file}.output"
  error_file="${file}.output.error"

  echo "[$iters]"
  echo "[INFO] running $file.sl ..."

  tmpfile=$(mktemp)

  # Execute the sl command with a timeout of 5 seconds and capture output and error
  timeout "$timeout_limit" "$sl" "$file.sl" &> "$tmpfile"
  exit_code=$?

  # Check if the command timed out
  if [ $exit_code -eq 124 ]; then
    echo "[ERROR] Test $file.sl exceeded the time limit of $timeout_limit seconds!"
    echo "[ERROR] $file.sl exceeded the time limit." >> suspicious.log
    ((fail++))
    continue
  fi

  # Check if the command was successful (exit code 0)
  if [ $exit_code -eq 0 ]; then
    # For successful execution, compare with the .output file
    if [ -f "$output_file" ]; then
      # Normalize line endings and remove empty lines
      diff <(sed 's/\r$//' "$tmpfile" | sed '/^$/d') <(sed 's/\r$//' "$output_file" | sed '/^$/d') > /dev/null
      if [ $? -eq 0 ]; then
        echo "[ OK ] Output for $file matches expected output."
        ((ok++))
      else
        echo "[ERROR] Output for $file does not match expected output!"
        echo "[ERROR] $file " >> wrong_stdout.log
        ((fail++))
        echo "--- Got ---"
        cat "$tmpfile"
        echo "--- Expected ---"
        cat "$output_file"
        echo "--- Diff ---"
        diff -y <(sed 's/\r$//' "$tmpfile") <(sed 's/\r$//' "$output_file")
      fi
    else
      echo "!!!! FATAL !!!! Output file $output_file not found for $file."
      echo "!!!! FATAL !!!! $output_file not found for $file." >> fatal_errors.log
      ((fatals++))
    fi
  else
    # For failed execution, compare with the .output.error file, if it exists
    if [ -f "$error_file" ]; then
      diff <(sed 's/\r$//' "$tmpfile" | sed '/^$/d') <(sed 's/\r$//' "$error_file" | sed '/^$/d') > /dev/null
      if [ $? -eq 0 ]; then
        echo "[ OK!] Error output for $file matches expected error."
        ((ok++))
      else
        echo "[ERROR] Error output for $file does not match expected error!"
        echo "[ERROR] $file" >> wrong_stderr.log
        ((fail++))
        echo "--- Got ---"
        cat "$tmpfile"
        echo "--- Expected ---"
        cat "$error_file"
        echo "--- Diff ---"
        diff -y <(sed 's/\r$//' "$tmpfile") <(sed 's/\r$//' "$error_file")
      fi
    else
      echo "!!!! FATAL !!!! Error file $error_file not found for $file."
      echo "!!!! FATAL !!!! $error_file not found for $file." >> fatal_errors.log
      ((fatals++))
    fi
  fi

  # Clean up temporary file after each iteration
  rm -f "$tmpfile"
done

echo "========== ALL DONE =========="
echo "  tests passed: ${ok}"
echo "  tests failed: ${fail}"
echo "  fatal errors: ${fatals}"
echo ""
