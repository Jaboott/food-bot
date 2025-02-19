# Restaurant Recommendation Bot

## Overview
A simple discord bot that helps user decide which restaurant to eat at when you can't make up your mind. 

## Features
- Suggest random restaurants from database
- Filter restaurants by cuisine type and location
- Add new restaurants to the database with options menu
- Remove restaurants from the database with keyword matching

## Commands
> Note: The bot uses Discord's slash commands
- Add
- Remove
- Random
- Restaurants

### Add

Add restaurants to database 

`/add RESTAURANT_NAME CUISINE_TYPE GENERAL_LOCATION`

<img src="https://i.imgur.com/dV8RjkH.png" height=250>

### Remove

Remove restaurants from the database

`/remove RESTAURANT_NAME`

<img src="https://i.imgur.com/GJOymV1.png" height=180>

### Random

Get a random restaurant from the database with or without filters

`/random FILTER_TYPE FILTER_LOCATION`

<img src="https://i.imgur.com/1jeTPbz.png" height="170">

### Restaurants

Display every restaurant stored in the database

`/restaurants`