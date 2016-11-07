@ECHO OFF

chdir /D "%~dp0..\..\client"
del uim2020-html5-demonstrator.zip
rmdir /s /q uim2020-html5-demonstrator-master

wget -q --no-check-certificate -Ouim2020-html5-demonstrator-master.zip https://github.com/cismet/uim2020-html5-demonstrator/archive/master.zip
jar xf uim2020-html5-demonstrator-master.zip

chdir /D uim2020-html5-demonstrator-master
call npm update
call bower update