#!/bin/bash 
# Absolute path to this script, e.g. /home/user/bin/foo.sh
SCRIPT=$(readlink -f "$0")

# Absolute path this script is in, thus /home/user/bin
SCRIPTPATH=$(dirname "$SCRIPT")

# Absolute path of the jar
JARFOLDERPATH=${SCRIPTPATH%scripts}target/json-to-csv-1.0-SNAPSHOT-jar-with-dependencies.jar

# Build maven jar
mvn clean compile assembly:single

#convert json to csv
java -cp $JARFOLDERPATH "com.jsontocsv.converter.App" $1 $2
