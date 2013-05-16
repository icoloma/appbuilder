# Configuración #

- SpringToolSuite 3.2.0.RELEASE
- JDK 1.7


Configurar el servidor para arrancar con el perfil adecuado (dev,prod)

-Dspring.profiles.active=dev


# Rest-Shell
Para realizar pruebas manuales del API

		git clone git://github.com/SpringSource/rest-shell.git
		cd rest-shell
		./gradlew installApp
		cd build/install/rest-shell-1.2.0.RELEASE
		bin/rest-shell
