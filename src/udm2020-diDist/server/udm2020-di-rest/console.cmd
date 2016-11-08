@ECHO OFF
set exec_path=%~dp0
chdir /D "%exec_path%"
REM call %exec_path%..\..\bin\setenv.cmd
java -jar -Xms64m -Xmx2g -Dlog4j.configuration="file:%exec_path%log4j.properties" -Djava.security.policy=policy.file "%exec_path%..\..\lib\starterUDM2020-di-rest\cids-server-rest-legacy-1.0-UBA-SNAPSHOT-starter.jar" @runtime.properties