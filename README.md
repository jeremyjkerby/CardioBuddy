<img align="left" src="/resources/logo.png" alt="logo"></img>
# Cardio Buddy

## Purpose
This application is intended for anyone of all ages and backgrounds who are looking for a way to keep track of their cardio workouts on general cardio equipment. Stationary Bike, Treadmill, Elliptical, and Stair Climber are currently supported. There is no need for any expensive or fancy sensors. Simply add data displayed from the cardio equipment at the end of every session.

## Overview
Normal users are able to interact with this software through the Android application. Currently, the server acts as a way for users to backup their informatiom if they desire.

### Server
The server is built with Python's Flask framework and uses a MySQL library. It provides a custom built API to communicate with multiple Android mobile devices. The server has simple authentication functionality using Auth0 JWT.

### Android
The Android application utilizes multi-window user interface, stores data locally on user devices using SQLite, and communicates with a headless server via a custom built API.

<img align="left" src="/resources/sample0.png" alt="Sample 0" height="330" width="210">
<img align="center" src="/resources/sample1.png" alt="Sample 1" height="330" width="210">
<img align="left" src="/resources/sample2.png" alt="Sample 2" height="330" width="210">
<img align="center" src="/resources/sample3.png" alt="Sample 3" height="330" width="210">

**Note:** This application will replace an older version deployed on [Google Play](https://play.google.com/store/apps/details?id=com.cardiobuddy)

## Installiation
1. Server
You may need to install the following project dependcies:
```bash
brew install python3
pip3 install flask mysql-connector pyjwt
```

Modify appsecret in server.py

Modify mysql.cfg settings

Run database_connector.py to setup the database.

Run the server. $ python3 server.py

2. Android

TODO.

Install from Android Studio.

## API Architecture
There are two main paths; user, workouts

Once there is a server setup, you may execute the following API calls using cURL in a terminal to perform some actions as you would in the Android application.

### User
Create new user:
```bash
curl -H "Content-type: application/json" -X POST http://127.0.0.1:5000/user?token=value -d '{"role": "value", "email": "value", "password": "value", "fname": "value", "lname": “value"}'
```
Get user:
```bash
curl -X GET "http://127.0.0.1:5000/user?token=value&email=value"

Example output:
{
  "user": [
    "admin", 
    "guido@python.com", 
    "2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b", 
    "Guido", 
    "van Rossum"
  ]
}
```
Delete user:
```bash
curl -X DELETE "http://127.0.0.1:5000/user?token=value&email=value"
```

### Workouts
Create new workouts:
```bash
curl -H "Content-type: application/json" -X POST http://127.0.0.1:5000/workouts?token=value -d '[{"u_id": value, "date": value, "type": value, "duration": value, "calories": value, "distance": value, "notes": "value"}, {"u_id": value, "date": value, "type": value, "duration": value, "calories": value, "distance": value, "notes": "value"}]'
```
Get all workouts:
```bash
curl -X GET "http://127.0.0.1:5000/workouts?token=value&u_id=value"

Example output:
{
  "workouts": {
    "9": {
      "calories": 805,
      "date": 1121213911,
      "distance": 100,
      "duration": 75,
      "note": "First workout.",
      "type": 2
    },
    "10": {
      "calories": 650,
      "date": 1121213911,
      "distance": 200,
      "duration": 60,
      "note": "Second workout.",
      "type": 3
    }
  }
}
```
Delete all workouts:
```bash
curl -X DELETE "http://127.0.0.1:5000/workouts?token=value&u_id=value"
```

## Author
Jeremy J. Kerby
