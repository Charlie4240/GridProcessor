# GridProcessor

GitHub Actions is configured for a native Android release build.

Toolchain:
- Ubuntu GitHub-hosted runner
- JDK 17 (Temurin)
- Gradle 8.4
- Android SDK 34
- Build Tools 34.0.0
- NDK 25.1.8937393
- CMake 3.22.1
- Kotlin 1.9.0
- Android Gradle Plugin 8.1.0

The repository root contains the `GridProcessor/` Android project.
The GitHub workflow builds `GridProcessor/app/build/outputs/apk/release/*.apk`
and uploads it as the `GridProcessor-release-apk` workflow artifact.

Note: the checked-in Gradle wrapper metadata does not include a wrapper JAR,
so GitHub Actions intentionally uses Gradle 8.4 from `gradle/actions/setup-gradle`
instead of invoking `./gradlew`.
