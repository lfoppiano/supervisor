#!/bin/bash

NO_ARGS=0
if [ $# -eq "$NO_ARGS" ]
then
   echo "Usage: $0 -i directory"
exit
fi

# check parameters

while getopts "i:" Option
do
   case $Option in
      i ) FILE=$OPTARG;;
   esac
done

#echo $FILE
mogrify -noise 2 "$FILE"
NAMEFILE=${FILE##*/}
#echo $NAMEFILE
NAMEFILE=${NAMEFILE%.*}
#echo $NAMEFILE
convert -threshold 28000 -colors 2  "$FILE" ${FILE%/*}/$NAMEFILE-test1.png
#echo ${FILE%/*}/$NAMEFILE-test1.png
#convert -colors 2 ${FILE%/*}/$NAMEFILE-test1.png ${FILE%/*}/$NAMEFILE-test2.png 
#echo  ${FILE%/*}/$NAMEFILE-test2.png
convert ${FILE%/*}/$NAMEFILE-test1.png "${FILE%.*}.tif"

rm ${FILE%/*}/$NAMEFILE-test1.png
#rm ${FILE%/*}/$NAMEFILE-test2.png
