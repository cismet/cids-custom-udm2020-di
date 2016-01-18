#!/bin/bash

EXEC_PATH="`dirname \"$0\"`"
EXEC_PATH="`( cd \"$EXEC_PATH\" && pwd )`"

RES_PATH=$EXEC_PATH/../lib/localUdm2020-di/

mvn -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -s $EXEC_PATH/settings.xml -Dcids.generate-lib.checkSignature=false -Dcids.generate-lib.sign=false $* clean package -U
wget https://github.com/cismet/cids-custom-udm2020-di/raw/dev/src/udm2020-diDist/lib/localUdm2020-di/res.jar --no-check-certificate  -O $RES_PATH/res.jar
