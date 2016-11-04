@ECHO OFF
set curr_dir=%cd% 
set exec_path=%~dp0

chdir /D %exec_path%..\..\client\
wget -q --no-check-certificate -Ouim2020-html5-demonstrator.zip https://github.com/cismet/uim2020-html5-demonstrator/archive/master.zip
jar xf uim2020-html5-demonstrator.zip udm2020-di-html5
del uim2020-html5-demonstrator.zip

chdir /D %exec_path%..\..\client\uim2020-html5-demonstrator-master
npm install

chdir /D %curr_dir%