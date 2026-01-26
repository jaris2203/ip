#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

overall=0

run_test () {
    testdir="$1"

    # delete output from previous run
    if [ -e "$testdir/ACTUAL.TXT" ]
    then
        rm "$testdir/ACTUAL.TXT"
    fi

    # run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
    java -classpath ../bin TalkingPal < "$testdir/input.txt" > "$testdir/ACTUAL.TXT"

    # convert to UNIX format
    cp "$testdir/EXPECTED.TXT" "$testdir/EXPECTED-UNIX.TXT"
    dos2unix "$testdir/ACTUAL.TXT" "$testdir/EXPECTED-UNIX.TXT"

    # compare the output to the expected output
    diff "$testdir/ACTUAL.TXT" "$testdir/EXPECTED-UNIX.TXT"
    if [ $? -eq 0 ]
    then
        echo "Test result: PASSED"
    else
        echo "Test result: FAILED"
        overall=1
    fi
}

run_test "lowerBasicTest"
run_test "upperBasicTest"

exit $overall
