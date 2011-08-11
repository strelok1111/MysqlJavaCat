cp -R dist/* deb_dist/usr/share/MysqlJavaCat
cp src/mysqljavacat/resources/about.png deb_dist/usr/share/MysqlJavaCat/MysqlJavaCat.png
chmod +x deb_dist/usr/share/MysqlJavaCat/MysqlJavaCat.jar
dpkg-deb -b deb_dist
mv deb_dist.deb MysqlJavaCat_0.2.10_all.deb
