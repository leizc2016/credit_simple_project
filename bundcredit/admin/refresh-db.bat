@echo off
echo [INFO] Re-create the schema and provision the sample data.

cd %~dp0

call mvn antrun:run -Prefresh-db


pause