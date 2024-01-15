#!/bin/sh
# jackpine1.0
#
# Spatial benchmark 
# (c) 2011 University of Toronto. All rights reserved. 

#BHOME=`dirname $0`/..
BHOME=$(pwd)

index() {
   str="$1"
   substr="$2"
   var=${str%%$substr*}
   echo "(${#var} + 1 ) % (${#str} + 1)" |bc
}

echo "$SHELL"
for scenario in "$BHOME"/config/*.properties
do
	Offset=$(expr "$scenario" : '[0-9a-zA-Z./]*connection[a-zA-Z._]')
	Ndex=$(expr "$scenario" : '[0-9a-zA-Z./]*log4j[a-zA-Z.]*')
	if [ "$Offset" = 0 ] && [ "$Ndex" = 0 ]; then
    OutputFile=$(echo "$scenario" | sed -e 's+^.*/++')
		Offset=$(echo "$OutputFile" | awk -F '.' '{print length($1)}')
		OutputFile=$(echo "$OutputFile" | cut -c 1-"$Offset")
 		echo "\nRunning scenario $scenario. Result is written in ./results/${OutputFile}.html"
		if [ $# = 2 ] && [ "$1" = "-i" ];  then
			./gradlew run --args="-props $scenario -html results/${OutputFile}.html -include $2"
		else	
			./gradlew run --args="-props $scenario -html results/${OutputFile}.html"
		fi
	fi
done
