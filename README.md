Images
---

[![Build Status](https://travis-ci.com/lmouline/2021-7Senders.svg?branch=main)](https://travis-ci.com/lmouline/2021-7Senders)


This is my implementation of the [coding challenge for SevenSenders](https://www.notion.so/Coding-challenge-f9c8f64da34e4d1998298ca2b579effa).


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
