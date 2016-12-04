@echo off
echo [Pre-Requirement] Makesure install JDK 6.0+ and set the JAVA_HOME.
echo [Pre-Requirement] Makesure install Maven 3.0.3+ and set the M2_HOME PATH.

set MVN=mvn
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

echo [Step 1] Install all bundcredit parent modules and archetype to local maven repository.
cd parent
call %MVN% clean install -Dmaven.test.skip=true
if errorlevel 1 goto error
cd ..\

echo [Step 1] Install all dinosaur core modules and archetype to local maven repository.
cd core
call %MVN% clean install -Dmaven.test.skip=true
if errorlevel 1 goto error
cd ..\


goto end
:error
echo Error Happen!!
:end
pause