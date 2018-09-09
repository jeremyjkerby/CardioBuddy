<img align="left" src="/resources/logo.png" alt="logo"></img>
# Cardio Buddy

## Purpose
This application is intended for anyone of all ages and backgrounds who are looking for a way to keep track of their cardio workouts on general cardio equipment. Stationary Bike, Treadmill, Elliptical, and Stair Climber are currently supported. There is no need for any expensive or fancy sensors. Simply add data displayed from the cardio equipment at the end of every session.

## Overview
Normal users are able to interact with this software through the Android application. Currently, the server acts as a way for users to backup their informatiom if they desire.

### Server
The server is built with Python's Flask framework and uses a MySQL library. It provides a custom built API to communicate with multiple Android mobile devices.

### Android
The Android application utilizes mult-window user interface, stores data locally on user devices using SQLite, and communicates with a headless server via a custom built API.

<img align="left" src="/resources/sample0.png" alt="Sample 0" height="330" width="210">
<img align="center" src="/resources/sample1.png" alt="Sample 1" height="330" width="210">
<img align="left" src="/resources/sample2.png" alt="Sample 2" height="330" width="210">
<img align="center" src="/resources/sample3.png" alt="Sample 3" height="330" width="210">

## Installiation
1. Server
```bash
brew install python3

pip3 install flask

pip3 install mysql-connector
```
2. Android

Install from standalone .APK file or install from AndroidStudio.

## API Architecture
There are two main paths; user, workouts

Once there is a server setup, you may execute the following API calls using cURL in a terminal to perform some actions as you would in the Android application.

### User
Create new user:
```bash
curl -H "Content-type: application/json" -X POST http://127.0.0.1:5000/user -d '{"role": "value", "email": "value", "password": "value", "fname": "value", "lname": "value"}'
```
Get user:
```bash
curl -X GET http://127.0.0.1:5000/user?email=value
```
Delete user:
```bash
curl -X DELETE http://127.0.0.1:5000/user?email=value
```

### Workouts
Create new workouts:
```bash
#todo
```
Get all workouts:
```bash
curl -X GET http://127.0.0.1:5000/workouts?u_id=value
```
Delete all workouts:
```bash
curl -X DELETE http://127.0.0.1:5000/workouts?u_id=value
```

## Author
Jeremy J. Kerby
