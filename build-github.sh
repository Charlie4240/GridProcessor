#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")"

echo "=== GridProcessor GitHub Android Build ==="

java -version
gradle --version

echo "=== Release APK build ==="
gradle clean :app:assembleRelease --stacktrace --console=plain --no-daemon

echo "=== APK output ==="
find app/build/outputs/apk/release -type f -name '*.apk' -print
