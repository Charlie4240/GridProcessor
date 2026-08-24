#!/bin/sh
set -eu

# Buddy runs this script from the extracted project root.
PROJECT_ROOT="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
cd "$PROJECT_ROOT"

echo "=== GridProcessor Buddy Build ==="
echo "PROJECT_ROOT=$PROJECT_ROOT"

# Portable JDK 17; never modify the container with apt/systemd.
if command -v java >/dev/null 2>&1; then
  JAVA_BIN="$(readlink -f "$(command -v java)")"
else
  JDK_DIR="/tmp/gridprocessor-jdk17"
  rm -rf "$JDK_DIR"
  mkdir -p "$JDK_DIR"
  curl -L --fail --retry 3 \
    "https://api.adoptium.net/v3/binary/latest/17/ga/linux/x64/jdk/hotspot/normal/eclipse" \
    -o "$JDK_DIR/jdk.tar.gz"
  tar -xzf "$JDK_DIR/jdk.tar.gz" -C "$JDK_DIR"
  JAVA_BIN="$(find "$JDK_DIR" -type f -path '*/bin/java' -print -quit)"
fi

if [ -z "${JAVA_BIN:-}" ]; then
  echo "ERROR: Java 17 not found"
  exit 127
fi

JAVA_HOME="$(dirname "$(dirname "$JAVA_BIN")")"
export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

java -version

chmod +x ./gradlew

echo "=== Gradle ==="
./gradlew --version

echo "=== Release APK build ==="
./gradlew clean :app:assembleRelease --stacktrace --console=plain --no-daemon

echo "=== APK FILES ==="
find "$PROJECT_ROOT/app/build/outputs/apk" -type f -name '*.apk' -print
