#
# Prepara el projecto para lanzar Karma 
#

if [ -n "$WORKSPACE" ];  # $WORKSPACE es definido por Jenkins	
then
   cd $WORKSPACE/template;
fi

npm install
bower install
grunt _prepare_project
karma start
