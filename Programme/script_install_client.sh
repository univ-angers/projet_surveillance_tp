#!/bin/bash
sudo apt-get update
sudo apt-get install -y ffmpeg default-jre git tcpdump
echo "setup java path"
if ! grep -q "JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64" "/etc/environment"; then
  echo "JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64" >> /etc/environment
fi
export JAVA_HOME
sudo ./etc/environment/
echo "getting the jar file ..."
if [ -d "/opt/client_surveillance/" ]; then
  sudo rm -rf  /opt/client_surveillance/
fi
sudo mkdir /opt/client_surveillance/
sudo mkdir /opt/tmp
cd /opt/tmp
if [ -d "projet_surveillance_tp" ]; then
  sudo rm -rf /opt/tmp/projet_surveillance_tp/
fi
git clone https://github.com/univ-angers/projet_surveillance_tp.git
cd /opt/tmp/projet_surveillance_tp/Programme
mv ClientEtudiant.jar /opt/client_surveillance/
echo "#!/bin/bash" > /opt/client_surveillance/startup.sh
echo "sudo java -jar /opt/client_surveillance/ClientEtudiant.jar" >> /opt/client_surveillance/startup.sh
ln -s /opt/client_surveillance/startup.sh /home/etudiant/Bureau/startup.sh
sudo chmod +x /opt/client_surveillance/startup.sh
if ! grep -q "%users ALL=(ALL) NOPASSWD: /opt/client_surveillance/ClientEtudiant.jar" "/etc/sudoers"; then
  echo "%users ALL=(ALL) NOPASSWD: /opt/client_surveillance/ClientEtudiant.jar" >> /etc/sudoers
fi
sudo chmod +x /opt/client_surveillance/ClientEtudiant.jar
