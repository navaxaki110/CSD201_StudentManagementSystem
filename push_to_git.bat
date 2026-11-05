@echo off
title Push Code to GitHub - CSD201
cd /d "%~dp0"
echo ========================================================
echo       DANG DAY CODE LEN GITHUB CHO NHOM
echo ========================================================
echo Repo: https://github.com/navaxaki110/CSD201_StudentManagementSystem.git
echo.
git push -u origin main
echo.
if %errorlevel% equ 0 (
    echo ========================================================
    echo  [THANH CONG] Code da duoc day len GitHub thanh cong!
    echo  Link repo: https://github.com/navaxaki110/CSD201_StudentManagementSystem
    echo ========================================================
) else (
    echo ========================================================
    echo  [!] Co loi xay ra hoac ban chua dang nhap GitHub.
    echo ========================================================
)
pause
