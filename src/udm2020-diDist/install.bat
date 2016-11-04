@ECHO OFF

chdir /d %~dp0
call %~dp0gen\gen_cids\update.cmd > install-cids.log

chdir /d %~dp0
call %~dp0gen\gen_rest\update.cmd > install-rest.log

chdir /d %~dp0
call %~dp0gen\gen_html5\update.cmd > install-html5.log