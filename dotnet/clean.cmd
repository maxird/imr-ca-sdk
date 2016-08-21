@echo off

goto :start

:clean_project
cd %1
rm -rf bin
rm -rf obj
rm -f project.lock.json
cd ..
exit /b 0

:clean_folder
cd %1
for /d /r %%f in (*) do call :clean_project %%f
cd ..
exit /b 0

:start
call :clean_folder src
call :clean_folder tests
