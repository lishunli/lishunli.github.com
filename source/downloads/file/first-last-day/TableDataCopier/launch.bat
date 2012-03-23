@echo off
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_29
set PATH=%JAVA_HOME%\bin;%PATH%

call java -jar TableDataCopier.jar

pause
rem pause