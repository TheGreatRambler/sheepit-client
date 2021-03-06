# **The only point for this fork is for the client to work on ARM processors (like the raspberry pi).**
# *ALSO, THIS MIGHT NOT WORK! NO PROMISES!*
# *I NEED SOME PULL REQUESTS*
## Files changed
* `/src/com/sheepit/client/Job.java`
* `/src/com/sheepit/client/Client.java`
* `/src/com/sheepit/client/hardware/cpu/CPU.java` (possibly a breaking change)
* `/src/com/sheepit/client/os/Linux.java`
## Recommended usage for raspi
`sudo java -jar sheepit-client.jar -cores 4 -password <your password> -ui text -login <your username> -memory 700 -rendertime 6 -compute-method CPU`
It is recommended to specify both `cores` and `memory` because the code to retrieve those items is not working.
## Dependencies
* `sudo apt-get install oracle-java7-jdk` (You can try `sudo apt-get install oracle-java8-jdk` using `java8` as well)
* Blender is not needed because it is hopefully installed by the script (if you have errors, run `sudo apt-get install blender`)
## Notes
* Please do not use this version with anything other than ARM, it may not work

# SheepIt Render Farm Client

[![Build Status](https://secure.travis-ci.org/laurent-clouet/sheepit-client.svg)](http://travis-ci.org/laurent-clouet/sheepit-client)

## Overview

SheepIt Render Farm Client is an *Open Source* client for the distributed render farm [**SheepIt**](https://www.sheepit-renderfarm.com).

## Compilation

You need Java 1.7. OpenJDK and Oracle are both supported.
You also need [ant](http://ant.apache.org/).
To create the jar file, simply type `ant` in the project's root directory.

## Usage

Once you have a jar file, you can view the usage by running:

    java -jar bin/sheepit-client.jar --help

When you are doing development work, you can use a mirror of the main site specially made for demo/dev. The mirror is located at **http://sandbox.sheepit-renderfarm.com**, and you can use it by passing `-server http://sandbox.sheepit-renderfarm.com` to your invocation of the client.

At the command line ui (-ui text / -ui oneLine) you could type in the following commands and press enter to controll the client:

* status: to get the current status of the client (paused, stoped, ...)
* priority <n>: to set the renderer process priority
* block: to block the current project
* pause: pause the client to request new jobs after the current frame has finished to render
* resume: resume the client after it was paused
* stop: stop the client after the current frame has finished
* cancel: cancel the stop request
* quit: stops the client directly without finishing the current frame
