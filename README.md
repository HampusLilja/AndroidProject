AndroidProject
==============

SoftwareEngineering

FINAL
==============
Release Notice for FINAL:
New from previous release is that now you can choose to display all chat rooms on the map and in the list of
Nearby Chat Rooms. Leave chat room when exiting the chat activity. A lot of refactoring.
We have support for setting a specific radius in the code but not yet implemented it in the UI.
We have support for setting password on a chat room on the server but not yet on the application.
When you enter a chat room you will get a welcome message from the server. This message will not be saved in 
the local log file.

All documentations are under /doc. Or linked thorugh the DeveloperManual.pdf. 
ProjectReflections.pdf represents all our reflection documents.

Known buggs and limitations:
You can not use åäö when writing a message. This is caused by GCM "content type related error".
If the application shuts down when you are in a chat room the server do not get the "leave chat room" info.
If you stay active in a chat room you can walk out of range and still be able send and receive messages.
UI only supports xhdpi.
If server is not online you can still see the chat rooms downloaded on previous runs.
The splash screen is time based so if you have slow connection you may have to click an extra time on 
the chat room list to connect to the server one more time.
  

HANDIN_05
==============
Release Notice for HANDIN_05: 
New from previous release server database is now Android Native SQLite. This is for enabling directdownload 
to device, with support for Android SQLite Libs instantly.The device now downloads the latest list of 
chatrooms during startup. Application is now able to store settings and changes locally on device. New for 
NearByChatrooms view is now that it reads in the downloaded database and uses that for creating a list of 
current chatrooms.
The user can hereby join current chatrooms with added function both in device and on server. The bug of 
application crashing when using "back-key" in android softkey buttons, instead of the inbuilt menu is now fixed.

Furthermore Robotium is implementet instead of Androids testing tools for a smoother devlop experience.
For those test including Google Maps we had to write manual test (see Test.pdf under docs) specification due to 
Google's privacy policy regarding thier maps.

HANDIN_04
==============
Release Notice for HANDIN_04:
In this release you can create a chat room at your location and it will show a shaded circle on the map.
The colour of the circle on the map depends on your location.
If you are in range of one or more chat room circles they will be green, all other circles will be red.
If the server (see README in folder: server-package) is up and running then  you can send messages from your
device to all other devices connected through our server and GCM.
We also did a huge amount of refactoring.

Known bugs and limitations for HANDIN_04:
Created chat rooms will not be saved if you shut down the application.
You can´t press the chat icon on the mennubar, you have to go through chat room to get to that activity.
No test for GpsActivity will be created because reasearch shows that Google do not approve to mock activitys
that extends MapActivty. We will create manual test specification for those test cases that are affected by this. 