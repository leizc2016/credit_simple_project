@echo off
echo [Pre-Requirement] Makesure install JDK 6.0+ and set the JAVA_HOME.
echo [Pre-Requirement] Makesure install Maven 3.0.3+ and set the M2_HOME PATH.

set MVN=mvn
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m


echo [Step 1] Package all bundcredit core modules and archetype to local maven repository.
cd core
call %MVN%  clean package -Dmaven.test.skip=true -Pproduction
if errorlevel 1 goto error
cd ..\

echo [Step 2] Package all bundcredit site modules and archetype to local maven repository.
cd site
call %MVN%  clean package -Dmaven.test.skip=true -Pproduction
if errorlevel 1 goto error
cd ..\

echo [Step 3] Package all bundcredit admin site modules and archetype to local maven repository.
cd admin
call %MVN%  clean package -Dmaven.test.skip=true -Pproduction
if errorlevel 1 goto error
cd ..\

goto end
:error
echo Error Happen!!
:end
pause