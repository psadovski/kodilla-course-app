call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo Cannot open runcrud.bat
goto fail

:openbrowser
start chrome
if "%ERRORLEVEL%" == "0" goto openlocalhost
echo Cannot open browser
goto fail

:openlocalhost
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.