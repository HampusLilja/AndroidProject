
 * This is a added server package for ShutAPP Android application. Created in Netbeans IDE 7.2
 * Copyright (C) 2012  Hampus Lilja, Tomas Arnesson, Mathias Dosé, Mathias Herzog
 * This program uses GNU General Public License, please read supplied LICENCE.TXT for more info.

 * INSTALL NOTES *
For installation, please Import Project into Netbeans and build/deploy.
The application also uses connection to external SQLite databases. Which can be created with for example SQLite Editor (freeware).
For connection use Java jdbc:sqlite, create a DB called SHUTAPPDB, see attached docs for table layout.
Use org.sqlite.JDBC as database driver, also currently the database path local, so make sure you change static String DBURL path in GCM_Server.java before deployment.
