#!/bin/bash
export AXIS_HOME=~/fib/pxc/prac5/axis-1_1
export TOMCAT_HOME=/usr/local/apache-tomcat-5.5.28
export CLASSPATH=.:$AXIS_HOME:$AXIS_HOME/lib/axis.jar:$AXIS_HOME/lib/jaxrpc.jar:$AXIS_HOME/lib/saaj.jar:$AXIS_HOME/lib/commons-logging.jar:$AXIS_HOME/lib/commons-discovery.jar:$AXIS_HOME/lib/wsdl4j.jar:$TOMCAT_HOME/webapps/axis/WEB-INF/lib/xerces.jar
