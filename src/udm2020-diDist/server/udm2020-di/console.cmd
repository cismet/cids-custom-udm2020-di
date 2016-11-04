@ECHO OFF
set exec_path=%~dp0
chdir /D %exec_path%
REM call %exec_path%..\..\bin\setenv.cmd
java -jar -Xms64m -Xmx2g -Djava.security.policy=policy.file %exec_path%..\..\lib\starterUDM2020-di\cids-custom-udm2020-di-server-1.0-SNAPSHOT-starter.jar