@echo off
chcp 65001

:: Get the current directory
set DIR=%~dp0
cd %DIR%

:: Copy resources
xcopy /s /e /y .\app\src\resources .\app\bin\resources\

:: Compile the source code
javac -encoding "UTF-8"  -sourcepath .\app\src\ .\app\src\logarlec\App.java -d .\app\bin

:: Run the application
java -cp .\app\bin logarlec.App