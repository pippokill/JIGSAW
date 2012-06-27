#!/bin/sh
#---------------------------------#
# dynamically build the classpath #
#---------------------------------#
THE_CLASSPATH=../dist/JIGSAW.jar
for i in `ls ../dist/lib/*.jar`
do
  THE_CLASSPATH=${THE_CLASSPATH}:${i}
done

java -Xmx1G -cp ".:${THE_CLASSPATH}" jigsaw.JIGSAW "$@"


