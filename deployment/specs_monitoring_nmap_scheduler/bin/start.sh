#!/bin/sh

# Reload profile if it exists.
if [ -f /etc/profile ];
then
    echo "Reload profile data"
    source /etc/profile
fi

# Use JAVA_HOME if it is set.
if [ ! -z "$JAVA_HOME" ];
then
    # JAVA_HOME is set, use it.
    $JAVA_HOME/bin/java -jar ../lib/Scheduler.jar -p specs_monitoring_nmap_scheduler.pid
else
    # JAVA_HOME is NOT set.
    java -jar ../lib/Scheduler.jar -p specs_monitoring_nmap_scheduler.pid
fi