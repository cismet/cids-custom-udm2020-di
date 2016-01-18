set curr_dir=%cd% 

set exec_path=%~dp0



chdir /D %exec_path%



call mvn -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -s %exec_path%\settings.xml -Dcids.generate-lib.checkSignature=false -Dcids.generate-lib.sign=false clean package -U



chdir /D %curr_dir%


wget --no-check-certificate -O..\lib\localUdm2020-di\res.jar https://github.com/cismet/cids-custom-udm2020-di/raw/dev/src/udm2020-diDist/lib/localUdm2020-di/res.jar