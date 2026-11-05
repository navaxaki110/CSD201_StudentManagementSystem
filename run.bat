@echo off
title Student Management System - CSD201
echo Dang bien dich ma nguon Java...
if not exist "bin" mkdir bin
javac --release 8 -encoding UTF-8 -d bin src\*.java
if %errorlevel% neq 0 (
    echo [!] Bien dich that bai! Kiem tra lai JDK.
    pause
    exit /b %errorlevel%
)
echo [OK] Bien dich thanh cong! Dang khoi dong ung dung...
echo.
java -cp bin Main
pause

