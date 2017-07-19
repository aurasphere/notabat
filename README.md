[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/aurasphere/notabat/blob/master/LICENSE)
[![release](http://github-release-version.herokuapp.com/github/aurasphere/notabat/release.svg?style=flat)](https://github.com/aurasphere/notabat/releases/latest)

# Notabat

### Overview
Windows low/full battery notifier application.

### Quickstart
You can either run the notabat jar in the bin folder (by double clicking it) or use the provided batch script in the same folder. You can also put the batch script into the Startup Windows folder in order to automatically start Notabat when the Windows starts. If you do so, remember to make sure that the notabat.jar file is also put into the same folder of the script or change the path on the script accordingly.

### Features
 - Detects 2 battery status in form of events: charging with battery full and not charging with battery low.
 - When an event is fired, a popup window with a message is shown.
 - Each event can be associated with an audio clip (user defined) that will be played. The audio clip may be different for each event.
 - The battery percentage level for the notification can be customized by the user along with the polling interval for checking the battery status. A small polling interval translates in faster showing the popup but it's more resource intensive.
 - The configuration is automatically generated the first time the program is run inside the "/Program files/Notabat" folder.
 - The configuration can be edited without restarting the application. New changes will be picked up at the next battery check.
 - The notification popup shows an estimate of the expected residual autonomy in minutes if this information is made available by your system.
 - The notification popup will have 1 of 10 random color each time. The colors used are [Windows 7.5 standard](https://msdn.microsoft.com/library/windows/apps/ff402557(v=vs.105).aspx): Lime, Green, Teal, Cyan, Violet, Pink, Magenta, Red, Orange, Brown. 

### Screenshots

UNDER CONSTRUCTION

### Contributions
If you want to contribute on this project, just fork this repo and submit a pull request with your changes. Improvements are always appreciated!

### Project status
This project is considered completed and won't be developed further unless I get any specific requests.

### Contacts
You can contact me using my account e-mail or opening an issue on this repo. I'll try to reply back ASAP.

### License and purpose of the project
This project was created mostly for personal user. I found really difficult to notice the Windows default battery notification when I was running full screen application (mostly online games) and that ended often up on my laptop shutting down. This is basically the history behind this program. A big popup with a sound to immediately notice that I had to plug-in the charger. Lately I've decided to make this project open-source since somebody else may want to tweak it according to his/her needs. The project is released under the MIT license, which lets you reuse the code for any purpose you want (even commercial) with the only requirement being copying this <a href="LICENSE">project license</a> on your project.

<sub>Copyright (c) 2017 Donato Rimenti</sub>
