@ECHO OFF

chdir /d %~dp0
call %~dp0bin\setenv.cmd

chdir /d %~dp0
call %~dp0gen\gen_cids\install.cmd > install-cids.log

chdir /d %~dp0
call %~dp0gen\gen_rest\install.cmd > install-rest.log

chdir /d %~dp0
call %~dp0gen\gen_html5\install.cmd > install-html5.log