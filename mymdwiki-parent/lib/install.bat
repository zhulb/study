@echo off

>install.log mvn install:install-file -Dfile="%~dp0\cpdetector_1.0.10.jar" -DgroupId=info.monitorenter -DartifactId=cpdetector -Dversion=1.0.10 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true