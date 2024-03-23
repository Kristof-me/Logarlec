@echo off

:: Get the current directory
set DIR=%~dp0
cd %DIR%

:: Compile the source code
javac -sourcepath .\app\src\ .\app\src\logarlec\App.java -d .\app\bin
java -cp .\app\bin logarlec.App