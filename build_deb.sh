#!/bin/bash
VERSION=`cat VERSION`
sed -i -r "s/Version: [0-9]\.[0-9]\.[0-9]{2}/Version: $VERSION/g" deb_dist/DEBIAN/control deb_dist/DEBIAN/control
sed -i -r "s/Application.version = [0-9]\.[0-9]\.[0-9]{2}/Application.version = $VERSION/g" src/mysqljavacat/resources/MysqlJavaCatApp.properties
sed -i -r "s/AppVersion=[0-9]\.[0-9]\.[0-9]{2}/AppVersion=$VERSION/g" Win/Mysqljavacat_lin.iss
ant -q jar
launch4j Win/mysqljavacat_exe.xml
iscc Win/Mysqljavacat_lin.iss
cp -R dist/* deb_dist/usr/share/MysqlJavaCat
cp src/mysqljavacat/resources/about.png deb_dist/usr/share/MysqlJavaCat/MysqlJavaCat.png
chmod +x deb_dist/usr/share/MysqlJavaCat/MysqlJavaCat.jar
dpkg-deb -b deb_dist
mv deb_dist.deb MysqlJavaCat.deb
