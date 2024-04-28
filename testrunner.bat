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

::If there is an input parameter, run the test for that file
if not "%1"=="" (
    ::if file does not exist, echo error message
    if not exist ".\data\input\%1" (
        echo "File does not exist"
        goto :EOF
    )
    call :RunTest ".\data\input\%1"
    call :CompareFiles ".\data\output\%1"
    pause
    goto :EOF
)
::else, run all the tests in the input directory
:: Loop through each file in .\data\input\
for %%F in (.\data\input\*.txt) do (
    call :RunTest "%%F"
)
::>NUL 2>NUL

:: Compare the output files with the expected output files
for %%F in (.\data\output\*.txt) do (
    call :CompareFiles "%%F"
)
pause
endlocal
EXIT

:RunTest
::echo name of the file: %~n1
type "%~1" | java -Dfile.encoding=UTF-8 -Xmx3g -classpath .\app\bin logarlec.App 1>".\data\output\%~n1.txt" 2>NUL
goto :EOF

:CompareFiles
fc /c /n "%~1" ".\data\expected\%~n1.txt" 2>tempErr.txt | findstr /v /c:"Comparing" /c:"FC: no differences encountered" >tempOut.txt

set /p RESULT=<tempOut.txt
set /p ERR=<tempErr.txt

if "!ERR!" NEQ "" ( :: if the error string is not empty
    echo "%~nx1 ERROR"
) else if "!RESULT!" == "***** .\DATA\OUTPUT\%~n1.txt" ( 
    echo "%~nx1 FAIL"
) else (
    echo "%~nx1 SUCCESS"
)

del tempOut.txt
del tempErr.txt
goto :EOF
