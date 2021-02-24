Images
---

[![Build Status](https://travis-ci.com/lmouline/2021-7Senders.svg?branch=main)](https://travis-ci.com/lmouline/2021-7Senders)


This is my implementation of the [coding challenge for SevenSenders](https://www.notion.so/Coding-challenge-f9c8f64da34e4d1998298ca2b579effa).

# How to?

## Build

### With Docker

We define a [Docker Compose](https://docs.docker.com/compose/) that builds and runs the web application.
Before running it, please make sure that your 8080 port is available.
If not, you need to modify the port mapping in `docker-compose.yml`.

After installing Docker Compose on your machine, you can execute the following command from the project's root folder: `docker-compose build`.
**Warning:** The command might take a while to be executed.


### From scratch

For this project, we use the [Gradle](https://gradle.org/) build tool.
But, we use it with a wrapper, so you do not have to install anything!
The only requirement is that you need Java 15 installed on your machine.

If you are on a Unix system, you need to execute the following command from the project's root folder: `./gradlew build`.
From Windows, you can use the `gradlew.bat` script in the same way: `.\gradlew.bat build`.
For more information regarding this script, please refer to the [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).


## Run

### From Docker

As for the build, you can use the Docker Compose file.
You can use the following command: `docker-compose up` (no need to build it first).

### Command line

The building process generates an executable jar file that can be found in `build/libs`.
You can directly execute it: `java -jar build/libs/strips-<VERSION>.jar`.

# Challenge

## Requirements

There is a publicly available API for the webcomic XKCD – [https://xkcd.com/json.html](https://xkcd.com/json.html)

And an RSS feed for PDL: [http://feeds.feedburner.com/PoorlyDrawnLines](http://feeds.feedburner.com/PoorlyDrawnLines)

Create a RESTful service that pulls last 10 strips from each and combines them in a single json feed (20 recent entries in total). The response should contain the following data for each entry:

- picture url
- title / description
- web url for browser view
- publishing date

Sort the resulting feed by publishing date from recent to older.

## Build & Run

The solution should contain a docker-compose configuration to build and run it at [http://localhost:8080](http://localhost:8080)

## Assessment

We have the following expectations towards your solution.

- It should work
- It is readable and comprehensible
- The code is structured reasonably and the architecture can be justified
- It has tests

## Publication

The solution sources should be hosted on github. Preferably the commit history should represent your iterative process.

## Finally

Please don't spend more than a couple of hours on this. We don't expect the solution to be perfect or surprisingly original. We expect it to work.

**Good luck!**
