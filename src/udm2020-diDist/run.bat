@ECHO OFF

chdir /d %~dp0
call %~dp0bin\setenv.cmd

chdir /d %~dp0
start server\udm2020-di\server.cmd

chdir /d %~dp0
start server\udm2020-di-rest\server-rest.cmd

chdir /d %~dp0client\uim2020-html5-demonstrator-master\
start http-server ./app -a 0.0.0.0 -p 80 --cors -c-1
