#!/bin/bash

# Folder containing the input files
folder="./tests"

if [ -e "$1" ] && [ -x "$1" ]; then
  sl="$1"
else
  echo "The file '$1' either does not exist or is not executable."
  exit -1
fi

ok=0
fail=0
fatals=0
iters=0

touch fatal_errors.log
echo "" > fatal_errors.log

touch wrong_stderr.log
echo "" > wrong_stderr.log

touch wrong_stdout.log
echo "" > wrong_stdout.log

touch suspicious.log
echo "" > suspicious.log

rm *.tmp

x=130
# Iterate over all files in the folder
#for file in "$folder"/*.sl; do
for file in $(find $folder -type f -name "*.sl"); do
  #  Skip if it's not a regular file
  [ -f "$file" ] || continue
  file="${file%.*}"

  ((iters++))

  # if [ "$iters" -lt 240 ]; then
  #     continue  # Skip this iteration
  # fi

  # Output and error files (assuming they are in the same folder and named after the input file)
  output_file="${file}.output"
  error_file="${file}.output.error"

  echo "[$iters]"
  echo "[INFO] running $file.sl ..."
  # Execute the sl command and capture output and error
  tmpfile=$(mktemp)
  # echo $tmpfile
  $sl "$file.sl" &> $tmpfile
  
  # output=$($sl < "$file.sl" 2> >(tee /dev/stderr))

  # Check if the command was successful (exit code 0)
  if [ $? -eq 0 ]; then
    # If successful, compare the output with the .output file
    if [ -f "$output_file" ]; then
      # diff <(echo "$output") "$output_file" > /dev/null
      diff $tmpfile $output_file
      if [ $? -eq 0 ]; then
        echo "[ OK ] Output for $file matches expected output."
        ((ok++))
      else
        echo "[ERROR] Output for $file does not match expected output!"
        echo "[ERROR] $file " >> wrong_stdout.log
        ((fail++))
        echo "--- Got ---"
        cat $tmpfile
        echo "--- Expected ---"
        cat $output_file
        echo ""
        echo "--- Diff ---"
        diff -y <(echo "$output") "$output_file"
        echo $?        
        echo ""
        diff -c <(echo "$output") "$output_file"
        echo $?echo $?
        echo ""
        diff -u <(echo "$output") "$output_file"        
        echo $?
        exit -1
      fi
    else
      echo "!!!! FATAL !!!! Output file $output_file not found for $file."
      echo "!!!! FATAL !!!! $output_file not found for $file."  >> fatal_errors.log
      ((fatals++))
    fi
  else
    # If the command failed, compare the error with the .error file
    if [ -f "$error_file" ]; then
      # diff <(echo "$output") "$error_file" > /dev/null
      diff $tmpfile $error_file
      if [ $? -eq 0 ]; then
        echo "[ OK!] Error output for $file matches expected error."
        ((ok++))
      else
        echo "[ERROR] Error output for $file does not match expected error!"
        echo "[ERROR] $file" >> wrong_stderr.log
        ((fail++))
        echo "--- Got ---"
        cat $tmpfile
        echo "--- Expected ---"
        cat $error_file
        echo ""
        echo "--- Diff ---"
        diff -y <(echo "$output") "$error_file"
        echo $?        
        echo ""
        diff -c <(echo "$output") "$error_file"
        echo $?echo $?
        echo ""
        diff -u <(echo "$output") "$error_file"        
        echo $?
        exit -1
      fi
    else
      echo "!!!! FATAL !!!!!: Error file $error_file not found for $file."
      echo "!!!! FATAL !!!!!: $error_file not found for $file." >> fatal_errors.log
      ((fatals++))
    fi
  fi
done

echo "========== ALL DONE =========="
echo "  tests passed: ${ok}"
echo "  tests failed: ${fail}"
echo "  fatal errors: ${fatals}"
echo ""
