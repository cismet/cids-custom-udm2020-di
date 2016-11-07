@ECHO OFF

chdir /d %~dp0
call %~dp0bin\setenv.cmd

chdir /d %~dp0
call %~dp0gen\gen_cids\update.cmd > update-cids.log

chdir /d %~dp0
call %~dp0gen\gen_rest\update.cmd > update-rest.log

chdir /d %~dp0
call %~dp0gen\gen_html5\update.cmd > update-html5.log