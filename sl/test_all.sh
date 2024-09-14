#!/bin/bash

# Folder containing the input files
folder="./tests"

ok=0
fail=0

# Iterate over all files in the folder
for file in "$folder"/*.sl; do
  # Skip if it's not a regular file
  [ -f "$file" ] || continue
  file="${file%.*}"

  # Output and error files (assuming they are in the same folder and named after the input file)
  output_file="${file}.output"
  error_file="${file}.output.error"

  # Execute the sl command and capture output and error
  output=$(./sl < "$file.sl" 2> >(tee /dev/stderr))

  # Check if the command was successful (exit code 0)
  if [ $? -eq 0 ]; then
    # If successful, compare the output with the .output file
    if [ -f "$output_file" ]; then
      diff <(echo "$output") "$output_file" > /dev/null
      if [ $? -eq 0 ]; then
        echo "[OK] Output for $file matches expected output."
        ((ok++))
      else
        echo "[ERROR] Output for $file does not match expected output!"
        ((fail++))
        echo "--- Got ---"
        echo $output
        echo "--- Expected ---"
        cat $output_file
        exit -1
      fi
    else
      echo "!!!! FATAL !!!! Output file $output_file not found for $file."
      exit -1
    fi
  else
    # If the command failed, compare the error with the .error file
    if [ -f "$error_file" ]; then
      diff <(echo "$output") "$error_file" > /dev/null
      if [ $? -eq 0 ]; then
        echo "[OK] Error output for $file matches expected error."
        ((ok++))
      else
        echo "[ERROR] Error output for $file does not match expected error!"
        ((fail++))
        echo "--- Got ---"
        echo $output
        echo "--- Expected ---"
        cat $error_file
        exit -1        
      fi
    else
      echo "!!!! FATAL !!!!!: Error file $error_file not found for $file."
      exit -1 
    fi
  fi
done

echo "========== ALL DONE =========="
echo "  tests passed: ${ok}"
echo "  tests failed: ${fail}"
echo ""