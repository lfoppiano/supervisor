#!/bin/bash

NO_ARGS=0
if [ $# -eq "$NO_ARGS" ]
then
   echo "Usage: $0 -i filename.tif"
exit
fi

#
# check parameters
#
while getopts ":i:" Option
do
   case $Option in
      i ) FILE=$OPTARG;;
   esac
done

tesseract $FILE "${FILE%.*}" -l mil 
cat "${FILE%.*}".txt
rm "${FILE%.*}".txt
