CP="bin"


for f in /home/work/develop/javawrk/js-studiocomm/configuration/org.eclipse.osgi/57/0/.cp/lib/*jar
do
    CP="$CP:$f"
done


for f in lib/*.jar
do
    CP="$CP:$f"
done




echo $CP

java -cp $CP gui/MainApp
