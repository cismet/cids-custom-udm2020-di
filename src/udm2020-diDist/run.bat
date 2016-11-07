@ECHO OFF

chdir /d %~dp0
call %~dp0gen\bin\setenv.cmd

chdir /d %~dp0
start server\udm2020-di\console.cmd

chdir /d %~dp0
start server\udm2020-di-rest\console.cmd

chdir /d %~dp0client\uim2020-html5-demonstrator-master\
npm start