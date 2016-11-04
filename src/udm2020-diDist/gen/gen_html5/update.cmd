@ECHO OFF

chdir /D "%~dp0..\..\client"
wget -q --no-check-certificate -Ouim2020-html5-demonstrator.zip https://github.com/cismet/uim2020-html5-demonstrator/archive/master.zip
jar xf uim2020-html5-demonstrator.zip udm2020-di-html5
del uim2020-html5-demonstrator.zip

chdir /D uim2020-html5-demonstrator-master
npm install
bower install