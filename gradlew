#!/bin/sh
# Gradle Wrapper bootstrap for CI environments.
# The project uses Gradle 8.4 (compatible with Android Gradle Plugin 8.1.0).
set -e
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_DIR="$APP_HOME/gradle/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/gradle-wrapper.jar"
mkdir -p "$WRAPPER_DIR"
if [ ! -f "$WRAPPER_JAR" ]; then
  URL="https://raw.githubusercontent.com/gradle/gradle/v8.4/gradle/wrapper/gradle-wrapper.jar"
  if command -v curl >/dev/null 2>&1; then
    curl -fL --retry 3 --connect-timeout 15 -o "$WRAPPER_JAR" "$URL"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$WRAPPER_JAR" "$URL"
  else
    echo "ERROR: curl or wget is required to bootstrap Gradle Wrapper." >&2
    exit 1
  fi
fi
exec java ${JAVA_OPTS:-} ${GRADLE_OPTS:-} -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
