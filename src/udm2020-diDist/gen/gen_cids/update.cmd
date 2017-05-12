@ECHO OFF

chdir /D "%~dp0"

del cids-distribution-uim2020-di-master.zip
rmdir /s /q cids-distribution-uim2020-di-master

wget -q --no-check-certificate -Ocids-distribution-uim2020-di-master.zip https://github.com/cismet/cids-distribution-uim2020-di/archive/master.zip
jar xf cids-distribution-uim2020-di-master.zip

cd cids-distribution-uim2020-di-master
call mvn -Djavax.net.ssl.trustStore="%~dp0\truststore.ks" -Djavax.net.ssl.trustStorePassword=changeit -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -s "%~dp0\settings.xml" -Dcids.generate-lib.checkSignature=false -Dcids.generate-lib.sign=false clean package -U

REM \gen\gen_cids\-> ..\..\
wget -q --no-check-certificate -O"%~dp0..\..\lib\localUdm2020-di\res.jar" https://github.com/cismet/cids-custom-udm2020-di/raw/master/src/udm2020-diDist/lib/localUdm2020-di/res.jar

chdir /D "%~dp0"