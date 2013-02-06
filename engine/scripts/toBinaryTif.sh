#!/bin/bash

PROCESS_DIR=/usr/share/supervisor-engine/scripts

NO_ARGS=0
if [ $# -eq "$NO_ARGS" ]
then
   echo "Usage: $0 -i directory"
exit
fi

# check parameters

while getopts ":i:" Option
do
   case $Option in
      i ) DIR=$OPTARG;;
   esac
done

mogrify -noise 1 "$DIR/*.png"
mogrify -threshold 45000 -colors 2  "$DIR/*.png"

#N=$(ls $DIR/*.png | wc -l)

for NAME in `ls $DIR/*.png`; do
	convert ${NAME} "${NAME%.*}.tif"

#	if [ $N == 1 ]; then
#		sh $PROCESS_DIR/process.sh -i $NAME 
#	else
#		sh $PROCESS_DIR/process.sh -i $NAME &
#	fi
#	((N-=1))
done

