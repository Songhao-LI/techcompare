#!/bin/bash
JAVA_OPTS=""

# check if ENVIRONMENT=PRODUCTION environment variable is set
if [ "$ENVIRONMENT" = "PRODUCTION" ]; then
    JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=production"
fi

# Start the Java application with the system property
exec java $JAVA_OPTS -jar /app.jar
