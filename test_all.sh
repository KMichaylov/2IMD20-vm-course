#!/bin/bash

# Folder containing the input files
folder="./tests"

if [ -e "$1" ] && [ -x "$1" ]; then
  sl="$1"
else
  echo "The file '$1' either does not exist or is not executable."
  exit -1
fi

# Convert Windows line endings to Unix line endings in the 'toy' script and all test files
sed -i 's/\r$//' "$sl"
find "$folder" -type f \( -name "*.sl" -o -name "*.output" -o -name "*.error" \) -exec sed -i 's/\r$//' {} +

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

rm -f *.tmp

# Iterate over all files in the folder
for file in $(find $folder -type f -name "*.sl"); do
  [ -f "$file" ] || continue
  file="${file%.*}"

  ((iters++))

  # Output and error files
  output_file="${file}.output"
  error_file="${file}.output.error"

  echo "[$iters]"
  echo "[INFO] running $file.sl ..."

  # Create temporary file for output
  tmpfile=$(mktemp)
  $sl "$file.sl" &> $tmpfile

  sed -i '/^[[:space:]]*$/d' $tmpfile
  if [ -f "$output_file" ]; then
    sed -i '/^[[:space:]]*$/d' "$output_file"
  fi
  if [ -f "$error_file" ]; then
    sed -i '/^[[:space:]]*$/d' "$error_file"
  fi

  # Check if the command was successful (exit code 0)
  if [ $? -eq 0 ]; then
    if [ -f "$output_file" ]; then
      diff -u $tmpfile $output_file > /dev/null
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
        diff -y "$tmpfile" "$output_file"
        exit -1
      fi
    else
      echo "!!!! FATAL !!!! Output file $output_file not found for $file."
      echo "!!!! FATAL !!!! $output_file not found for $file."  >> fatal_errors.log
      ((fatals++))
    fi
  else
    if [ -f "$error_file" ]; then
      diff -u $tmpfile $error_file > /dev/null
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
        diff -y "$tmpfile" "$error_file"
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
