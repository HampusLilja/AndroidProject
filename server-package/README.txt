 * ShutApp Server-package v3, 2012-10-21
 * This is a added server package for ShutAPP Android application. Created in Netbeans IDE 7.2
 * Copyright (C) 2012  Hampus Lilja, Tomas Arnesson, Mathias Dosé, Mathias Herzog
 * This program uses GNU General Public License, please read supplied LICENCE.TXT for more info.

 * INSTALL NOTES *
For installation, please Import Project ShutAPP_GCM_SERVER into Netbeans IDE 7.2.
The application also uses connection to external SQLite databases.
A DB is included in project, ".\ShutAPP_GCM_SERVER\target\ShutAPP_GCM_SERVER-1.0-SNAPSHOT\shutappdb_v3.db".

Check GCM_Server.java and index.jspx in top code, to check local path to 
Static string DBURL in GCM_Server.java, and <sql:setDatasource url=""> in index.jspx.
Then build/deploy project!!

If u wish to use/inspect DB within Netbeans, use jdbc:sqlite driver. (org.sqlite.jdbc), and connect.

On deployment default webbrowser will open up and the added web test-environment will appear.
Here you can send messages to chatrooms, view database data, join/leave chatrooms with specific device ids and create new chatrooms on chosen gps coordinates.
