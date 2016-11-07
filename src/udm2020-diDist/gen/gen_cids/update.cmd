@ECHO OFF

chdir /D "%~dp0"
call mvn -Djavax.net.ssl.trustStore="%~dp0..\truststore.ks" -Djavax.net.ssl.trustStorePassword=changeit -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -s "%~dp0..\settings.xml" -Dcids.generate-lib.checkSignature=false -Dcids.generate-lib.sign=false clean package -U

wget -q --no-check-certificate -O"%~dp0..\..\lib\localUdm2020-di\res.jar" https://github.com/cismet/cids-custom-udm2020-di/raw/dev/src/udm2020-diDist/lib/localUdm2020-di/res.jar