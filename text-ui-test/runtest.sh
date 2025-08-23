#!/usr/bin/env bash
set -e

mkdir -p ../bin
rm -f ACTUAL.TXT ACTUAL-UNIX.TXT EXPECTED-UNIX.TXT

if ! find ../src/main/java -name "*.java" -print0 | xargs -0 javac -cp ../src/main/java -Xlint:none -d ../bin; then
  echo "********** BUILD FAILURE **********"
  exit 1
fi

java -classpath ../bin Rex < input.txt > ACTUAL.TXT

awk '{sub(/\r$/,""); print}' EXPECTED.TXT > EXPECTED-UNIX.TXT
awk '{sub(/\r$/,""); print}' ACTUAL.TXT > ACTUAL-UNIX.TXT
# Additional Tests
if diff -u ACTUAL-UNIX.TXT EXPECTED-UNIX.TXT; then
  echo "Test result: PASSED"
  exit 0
else
  echo "Test result: FAILED"
  exit 1
fi
