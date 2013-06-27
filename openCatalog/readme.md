# Configuración desarrollo local #

- SpringToolSuite 3.2.0.RELEASE
- JDK 1.7
- MongoDB 2.4.3 corriendo sobre el puerto 27017 (default)

# Spring Tool Suite
En caso de que problemas al importar el proyecto, hay que modificar el .project y sustituir el contenido por:

		<?xml version="1.0" encoding="UTF-8"?>
		<projectDescription>
			<name>openCatalog</name>
			<buildSpec>
				<buildCommand>
					<name>org.eclipse.wst.common.project.facet.core.builder</name>
				</buildCommand>
				<buildCommand>
					<name>org.eclipse.jdt.core.javabuilder</name>
				</buildCommand>
				<buildCommand>
					<name>org.springframework.ide.eclipse.core.springbuilder</name>
				</buildCommand>
				<buildCommand>
					<name>org.eclipse.wst.validation.validationbuilder</name>
				</buildCommand>
			</buildSpec>
			<natures>
				<nature>org.springframework.ide.eclipse.core.springnature</nature>
				<nature>org.eclipse.jdt.core.javanature</nature>
				<nature>org.springsource.ide.eclipse.gradle.core.nature</nature>
				<nature>org.eclipse.wst.common.project.facet.core.nature</nature>
				<nature>org.eclipse.wst.common.modulecore.ModuleCoreNature</nature>
			</natures>
		</projectDescription>



Configurar el servidor (tcServer) para arrancar con el perfil adecuado (dev,prod)
(doble click en el server --> Open Launch configuration --> Arguments)

		-Dspring.profiles.active=dev

# Datos iniciales
Para realizar una carga inicial de los datos se puede ejecutar **MongoDbPopulator**.
Hay que tener en cuenta que los test de los repositorios eliminan toda la información de las colecciones. 

# Postman - REST Client
Una opción para realizar las prubas manuales es a través del Plugin de Chrome [Postman- REST Client](https://chrome.google.com/webstore/detail/postman-rest-client/fdmmgilgnpjigdojojpjoooidkmcomcm)


# Rest-Shell
Otra opción para realizar pruebas manuales del API es [REST-SHELL](https://github.com/SpringSource/rest-shell)

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












 