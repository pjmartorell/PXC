#!/bin/bash

sudo /usr/local/apache-tomcat-5.5.28/bin/shutdown.sh		
javac lloguers/Lloguers.java
java org.apache.axis.wsdl.Java2WSDL -o lloguers.wsdl -l"http://localhost:8000/axis/services/lloguers" -n urn:lloguers -p"lloguers" urn:lloguers lloguers.Lloguers
java org.apache.axis.wsdl.WSDL2Java -o . -d Session -s -p lloguers.ws lloguers.wsdl
javac -source 1.4 lloguers/ws/*.java
jar cvf lloguers.jar lloguers/*.class lloguers/ws/*.class 
mv lloguers.jar /usr/local/apache-tomcat-5.5.28/webapps/axis/WEB-INF/lib/
java org.apache.axis.client.AdminClient -p8000 lloguers/ws/deploy.wsdd
sudo /usr/local/apache-tomcat-5.5.28/bin/startup.sh

