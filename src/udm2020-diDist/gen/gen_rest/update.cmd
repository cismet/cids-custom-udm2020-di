@ECHO OFF
set curr_dir=%cd% 
set exec_path=%~dp0

chdir /D %exec_path%

call mvn -Djavax.net.ssl.trustStore=%exec_path%..\truststore.ks -Djavax.net.ssl.trustStorePassword=changeit -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -s %exec_path%..\settings.xml -Dcids.generate-lib.checkSignature=false -Dcids.generate-lib.sign=false clean package -U

chdir /D %curr_dir%