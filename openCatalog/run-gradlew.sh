#
# Jenkis da problemas al usar gradlew en un directorio que no sea el 
# root del proyecto
#

if [ -n "$WORKSPACE" ];  # $WORKSPACE es definido por Jenkins   
then
   cd $WORKSPACE/openCatalog
fi

./gradlew clean check cobertura war

