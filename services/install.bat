@echo off
echo [Pre-Requirement] Makesure install JDK 6.0+ and set the JAVA_HOME.
echo [Pre-Requirement] Makesure install Maven 3.0.3+ and set the M2_HOME PATH.

set MVN=mvn
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

echo [Step 1] Install all service parent modules and archetype to local maven repository.
cd bcparent
call %MVN% clean install -Dmaven.test.skip=true
if errorlevel 1 goto error
cd ..\


goto end
:error
echo Error Happen!!
:end
pause