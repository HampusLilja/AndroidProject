 * ShutApp Server-Package v.3
 * This is a added server package for ShutAPP Android application. Created in Netbeans IDE 7.2
 * Copyright (C) 2012  Hampus Lilja, Tomas Arnesson, Mathias Dosé, Mathias Herzog
 * This program uses GNU General Public License, please read supplied LICENCE.TXT for more info.

 * INSTALL NOTES *
For installation, please Import Project into Netbeans and build/deploy.
This updated version now uses SQLite Android native database instead of Derby.
For usage make sure you have org.sqlite.JDBC driver installed and added to dependency in Netbeans.
Also for now the path to shutappdb.db is static, so make sure its valid in top of GCM_Server java class.
Gcm Server package now included in com.google.android.gcm.