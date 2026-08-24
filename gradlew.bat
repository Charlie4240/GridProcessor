@echo off
setlocal
set "APP_HOME=%~dp0"
set "WRAPPER_DIR=%APP_HOME%gradle\wrapper"
set "WRAPPER_JAR=%WRAPPER_DIR%\gradle-wrapper.jar"
if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"
if not exist "%WRAPPER_JAR%" (
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -UseBasicParsing -Uri 'https://raw.githubusercontent.com/gradle/gradle/v8.0.2/gradle/wrapper/gradle-wrapper.jar' -OutFile '%WRAPPER_JAR%'"
  if errorlevel 1 exit /b %errorlevel%
)
java %JAVA_OPTS% %GRADLE_OPTS% -classpath "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
endlocal
