#!/bin/bash

# Folder containing the input files
folder="./tests/generic"

# Check if an argument for the directory was provided
if [ -n "$2" ] && [ -d "$2" ]; then
  cd "$2" || { echo "Failed to change to directory $2"; exit 1; }
else
  echo "Using default folder: $folder"
fi

# Check if the given file is executable
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

# Initialize log files
touch fatal_errors.log wrong_stderr.log wrong_stdout.log suspicious.log failed_tests.log
echo "" > fatal_errors.log
echo "" > wrong_stderr.log
echo "" > wrong_stdout.log
echo "" > suspicious.log
echo "" > failed_tests.log

# Suppress errors when no .tmp files exist
rm -f *.tmp

# Iterate over all .sl files in the folder
for file in $(find $folder -type f -name "*.sl"); do
  # Skip if it's not a regular file
  [ -f "$file" ] || continue
  file="${file%.*}"

  ((iters++))

  # Output and error files (assuming they are in the same folder and named after the input file)
  output_file="${file}.output"
  error_file="${file}.output.error"

  echo "[$iters]"
  echo "[INFO] running $file.sl ..."

  tmpfile=$(mktemp)

  # Execute the sl command and capture output and error
  $sl "$file.sl" &> "$tmpfile"
  exit_code=$?

  # Check if the command was successful (exit code 0)
  if [ $exit_code -eq 0 ]; then
    # If successful, compare the output with the .output file
    if [ -f "$output_file" ]; then
      # Strip out trailing carriage return (CR) and empty lines for compatibility between Linux and Windows
      diff <(sed 's/\r$//' "$tmpfile" | sed '/^$/d') <(sed 's/\r$//' "$output_file" | sed '/^$/d') > /dev/null
      if [ $? -eq 0 ]; then
        echo "[ OK ] Output for $file matches expected output."
        ((ok++))
      else
        echo "[ERROR] Output for $file does not match expected output!"
        echo "[ERROR] $file " >> wrong_stdout.log
        echo "$file" >> failed_tests.log   # Log failing test name
        ((fail++))
        echo "--- Got ---"
        cat "$tmpfile"
        echo "--- Expected ---"
        cat "$output_file"
        echo ""
        echo "--- Diff ---"
        diff -y "$tmpfile" "$output_file"
      fi
    else
      echo "!!!! FATAL !!!! Output file $output_file not found for $file."
      echo "!!!! FATAL !!!! $output_file not found for $file."  >> fatal_errors.log
      echo "$file" >> failed_tests.log  # Log fatal test name
      ((fatals++))
    fi
  else
    # If the command failed, compare the error with the .error file
    if [ -f "$error_file" ]; then
      # Handle line ending compatibility and empty lines for errors
      diff <(sed 's/\r$//' "$tmpfile" | sed '/^$/d') <(sed 's/\r$//' "$error_file" | sed '/^$/d') > /dev/null
      if [ $? -eq 0 ]; then
        echo "[ OK!] Error output for $file matches expected error."
        ((ok++))
      else
        echo "[ERROR] Error output for $file does not match expected error!"
        echo "[ERROR] $file" >> wrong_stderr.log
        echo "$file" >> failed_tests.log  # Log failing test name
        ((fail++))
        echo "--- Got ---"
        cat "$tmpfile"
        echo "--- Expected ---"
        cat "$error_file"
        echo ""
        echo "--- Diff ---"
        diff -y "$tmpfile" "$error_file"
      fi
    else
      echo "!!!! FATAL !!!!!: Error file $error_file not found for $file."
      echo "!!!! FATAL !!!!!: $error_file not found for $file." >> fatal_errors.log
      echo "$file" >> failed_tests.log  # Log fatal test name
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
