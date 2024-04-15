setlocal enabledelayedexpansion
@echo off 
cls

:: Get the current directory
set DIR=%~dp0
cd %DIR%

:: Compile the source code
javac -sourcepath .\app\src\ .\app\src\logarlec\App.java -d .\app\bin >NUL 2>NUL

:: If there is no output directory, create one
if not exist .\data\output\ mkdir .\data\output\

:: Loop through each file in .\data\input\
for %%F in (.\data\input\*.txt) do (
    type "%%F" | java -Dfile.encoding=UTF-8 -classpath .\app\bin logarlec.App 1>".\data\output\%%~nF.txt" 
)
::>NUL 2>NUL

:: Compare the output files with the expected output files
for %%F in (.\data\output\*.txt) do (
    fc /c /n "%%F" ".\data\expected\%%~nF.txt" 2>tempErr.txt | findstr /v /c:"Comparing" /c:"FC: no differences encountered" >tempOut.txt

    set /p RESULT=<tempOut.txt
    set /p ERR=<tempErr.txt

    if "!ERR!" NEQ "" ( :: if the error string is not empty
        echo "%%~nxF ERROR"
    ) else if "!RESULT!" == "***** .\DATA\OUTPUT\%%~nF.txt" ( 
        echo "%%~nxF FAIL"
    ) else (
        echo "%%~nxF SUCCESS"
    )

    del tempOut.txt
    del tempErr.txt
)

endlocal