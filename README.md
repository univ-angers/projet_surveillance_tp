# Projet de surveillance de TP sur machine

Bonjour! Ce programme permet la surveillance de multiples postes client à partir d'une interface web. Il est constitué d'une partir serveur et d'une partie client.


# Serveur

## Installation
### FFMPEG

Dans un terminal: 
>sudo apt-get install ffmpeg
### OpenJDK

Dans un terminal:
>sudo apt-get install default-jre 
>sudo gedit /etc/environment 

Ajouter à la fin du fichier:
>JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 
>export JAVA_HOME 
	
Dans le terminal:
>. /etc/environment/ 

### Tomcat
Dans un terminal:
>sudo apt-get install wget<br />
>wget http://apache.mediamirrors.org/tomcat/tomcat-9/v9.0.13/bin/apache-tomcat-9.0.13.zip -O temp.zip<br />
unzip temp.zip -d ~/<br />
>rm temp.zip<br />
>cd ~/apache-tomcat-9.0.13/bin/<br />
>chmod +x ./catalina.sh<br />
>chmod +x ./startup.sh<br />
>chmod +x ./shutdown.sh

Placer le fichier ServeurJEE.war dans le dossier apache-tomcat-9.0.13/webapps

### Mysql
Dans un terminal:
>sudo apt install mysql-server

Depuis le dossier BDD téléchargé, dans un terminal:
>mysql -u root -p < ./utilisateurSurv.sql
>mysql -u root -p projetsurv < ./tablesSurv.sql


## Lancement
### Serveur web
Dans un terminal:
>sudo ~/apache-tomcat-9.0.13/bin/startup.sh

### Serveur vidéo
Dans un terminal:
>sudo java -jar ServeurVideo.jar


# Client
## Installation
### FFMPEG
Dans un terminal
>sudo apt-get install ffmpeg

### TCPDUMP
Dans un terminal
>sudo apt-get install tcpdump

### OpenJDK
Dans un terminal:
>sudo apt-get install default-jre

## Lancement
Dans un terminal:
>sudo java -jar ClientEtudiant.jar

Le **sudo** **est** **obligatoire** dans le cas d'une surveillance internet
