# Configuración desarrollo local #

- SpringToolSuite 3.2.0.RELEASE
- JDK 1.7
- MongoDB 2.4.3 corriendo sobre el puerto 27017 (default)


Configurar el servidor (tcServer) para arrancar con el perfil adecuado (dev,prod)
(doble click en el server --> Open Launch configuration --> Arguments)


		-Dspring.profiles.active=dev

# Datos iniciales
Para realizar una carga inicial de los datos se puede ejecutar **MongoDbPopulator**.
Hay que tener en cuenta que los test de los repositorios eliminan toda la información de las colecciones. 

# Rest-Shell
Para realizar pruebas manuales del API podemos utilizar [REST-SHELL](https://github.com/SpringSource/rest-shell)

Download the binary [.tar.gz](https://github.com/SpringSource/rest-shell/downloads) file

		$ tar -zxvf rest-shell-1.2.0.RELEASE.tar.gz
		$ cd rest-shell-1.2.0.RELEASE
		$ bin/rest-shell
		
		 ___ ___  __ _____  __  _  _     _ _  __    
		| _ \ __/' _/_   _/' _/| || |   / / | \ \   
		| v / _|`._`. | | `._`.| >< |  / / /   > >  
		|_|_\___|___/ |_| |___/|_||_| |_/_/   /_/   
		1.2.1.RELEASE
		
		Welcome to the REST shell. For assistance hit TAB or type "help".
		http://localhost:8080:> follow opencatalog/api
		http://localhost:8080/opencatalog/api:>  discover
		rel    href                                     
		================================================
		poi    http://localhost:8080/opencatalog/api/poi












 