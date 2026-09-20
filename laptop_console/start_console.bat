@echo off
echo ========================================================
echo  iQOO Office Kit - Laptop Sentinel Console
echo ========================================================
echo.
echo Starting Sentinel Console Server on http://localhost:3000...
start "" "http://localhost:3000"
node server.js
pause
