#!/bin/bash

echo "installing required package..."
sudo apt-get update
sudo apt-get install -y ffmpeg default-jre curl wget git mysql-server screen
echo "setup java path"
if ! grep -q "JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64" "/etc/environment"; then
  echo "JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64" >> /etc/environment
fi
export JAVA_HOME
sudo ./etc/environment/

echo "installing tomcat 9..."
sudo groupadd tomcat
sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
cd /tmp
if [ -d "/opt/tmp/projet_surveillance_tp" ]; then
  echo "suppression du dossier git existant..."
  sudo rm -rf /opt/tmp/projet_surveillance_tp/
fi
sudo curl -O http://mirror.cc.columbia.edu/pub/software/apache/tomcat/tomcat-9/v9.0.16/bin/apache-tomcat-9.0.16.tar.gz
if [ -d "/opt/tomcat" ]; then
  echo "suppression du dossier tomcat existant..."
  sudo rm -rf /opt/tomcat
fi
sudo mkdir /opt/tomcat

sudo gzip -d apache-tomcat-9.0.16.tar.gz
sudo tar xvf apache-tomcat-9.0.16.tar -C /opt/tomcat --strip-components=1
#cloning git
echo "getting the war file ..."
sudo mkdir /opt/tmp
cd /opt/tmp
git clone https://github.com/univ-angers/projet_surveillance_tp.git
cd projet_surveillance_tp/
mv /opt/tmp/projet_surveillance_tp/Programme/ServeurJEE.war /opt/tomcat/webapps/
#continuing the installation
cd /opt/tomcat
sudo chgrp -R tomcat /opt/tomcat
sudo chmod -R g+r conf
sudo chmod g+x conf
sudo chown -R tomcat webapps/ work/ temp/ logs/
echo "creating the service..."
echo "[Unit]">/etc/systemd/system/tomcat.service
echo "Description=Apache Tomcat Web Application Container">>/etc/systemd/system/tomcat.service
echo "After=network.target">>/etc/systemd/system/tomcat.service
echo "[Service]">>/etc/systemd/system/tomcat.service
echo "Type=forking">>/etc/systemd/system/tomcat.service
echo "Environment=JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64">>/etc/systemd/system/tomcat.service
echo "Environment=CATALINA_PID=/opt/tomcat/temp/tomcat.pid">>/etc/systemd/system/tomcat.service
echo "Environment=CATALINA_HOME=/opt/tomcat">>/etc/systemd/system/tomcat.service
echo "Environment=CATALINA_BASE=/opt/tomcat">>/etc/systemd/system/tomcat.service
echo "Environment='CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC'">>/etc/systemd/system/tomcat.service
echo "Environment='JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'">>/etc/systemd/system/tomcat.service
echo "ExecStart=/opt/tomcat/bin/startup.sh">>/etc/systemd/system/tomcat.service
echo "ExecStop=/opt/tomcat/bin/shutdown.sh">>/etc/systemd/system/tomcat.service
echo "User=tomcat">>/etc/systemd/system/tomcat.service
echo "Group=tomcat">>/etc/systemd/system/tomcat.service
echo "UMask=0007">>/etc/systemd/system/tomcat.service
echo "RestartSec=10">>/etc/systemd/system/tomcat.service
echo "Restart=always">>/etc/systemd/system/tomcat.service
echo "[Install]">>/etc/systemd/system/tomcat.service
echo "WantedBy=multi-user.target">>/etc/systemd/system/tomcat.service
sudo systemctl daemon-reload
sudo systemctl start tomcat
sudo ufw allow 8080
sudo systemctl enable tomcat
if [ -d "/opt/data_dir" ]; then
  echo "suppression du dossier git existant..."
  sudo rm -rf /opt/data_dir
fi
sudo mkdir /opt/data_dir
sudo chgrp -R tomcat /opt/data_dir
sudo chown -R tomcat /opt/data_dir

echo "installing the UDP server..."
cd /opt/tmp/projet_surveillance_tp/Programme
mkdir /opt/server_udp
mv ServeurVideo.jar /opt/server_udp
sudo chgrp -R tomcat /opt/server_udp
sudo chown -R tomcat /opt/server_udp
#if ! grep -q "java -jar /opt/server_udp/ServeurVideo.jar" "/opt/tomcat/bin/startup.sh"; then
#  sed '$ i\java -jar /opt/server_udp/ServeurVideo.jar &' /opt/tomcat/bin/startup.sh
#fi
sudo systemctl restart tomcat
cd /opt/tmp/projet_surveillance_tp/Programme/BDD/
echo "#################################################"
echo "restore database, enter root password if needed...."
mysql -u root -p < ./utilisateurSurv.sql
mysql -u root -p projetsurv < ./tablesSurv.sql

echo "creating the script for launching the server..."

echo "#!/bin/bash">/opt/start_surveillance_tp.sh
echo "#script for launching the tomcat server and video server">>/opt/start_surveillance_tp.sh
echo "sudo systemctl start tomcat.service">>/opt/start_surveillance_tp.sh
echo "sudo screen java -jar /opt/server_udp/ServeurVideo.jar &">>/opt/start_surveillance_tp.sh
sudo chmod +x /opt/start_surveillance_tp.sh
sudo chmod +x /opt/server_udp/ServeurVideo.jar
