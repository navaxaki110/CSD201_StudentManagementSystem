@echo off
title Student Management System - CSD201
echo Dang bien dich ma nguon Java...
if not exist "bin" mkdir bin
javac --release 8 -encoding UTF-8 -d bin src\model\*.java src\list\*.java src\queue\*.java src\tree\*.java src\manager\*.java src\view\*.java
if %errorlevel% neq 0 (
    echo [!] Bien dich that bai! Kiem tra lai JDK.
    pause
    exit /b %errorlevel%
)
echo [OK] Bien dich thanh cong! Dang khoi dong ung dung...
echo.
java -cp bin view.Main
pause
