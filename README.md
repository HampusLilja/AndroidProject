AndroidProject
==============

SoftwareEngineering

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