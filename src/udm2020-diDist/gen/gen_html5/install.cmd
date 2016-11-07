@ECHO OFF

chdir /D "%~dp0..\..\client"
jar xf uim2020-html5-demonstrator-master.zip

chdir /D uim2020-html5-demonstrator-master
npm install
bower install