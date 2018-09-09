"""server.py

    Simple server with some simple restful paths 
"""
from flask import Flask, request
import json
import controller as c


#TODO build custom error types with @app.errorhandler

app = Flask('/')

@app.route("/user", methods = ['GET', 'POST', 'DELETE'])
def api_user():
    if request.method == 'GET':
        if 'email' in request.args:
            print("here")
            users = c.getUserDB(request.args['email'])
            return str(users) + '\n'
        else:
            return "ERROR\n"
    elif request.method == 'POST':
        if request.headers['Content-Type'] == 'application/json':
            data = request.json
            c.addUserDB((data['role'], data['email'], data['password'], data['fname'], data['lname']))
            return "SUCCESS\n"
        else:
            return "ERROR\n"
    elif request.method == 'DELETE':
        if 'email' in request.args:
            c.deleteUserDB(request.args['email'])
            return "SUCCESS\n"
        else:
            return "ERROR\n"

@app.route("/workouts", methods = ['GET', 'POST', 'DELETE'])
def api_workouts():
    if request.method == 'GET':
        if 'u_id' in request.args:
            workouts = c.getAllWorkoutsDB(request.args['u_id'])
            return str(workouts) + '\n'
        else:
            return "ERROR\n"
    elif request.method == 'POST':
        #TODO clean this up
        workouts = []
        workouts.append((1, 1112323218, 3, 60, 532, 122, "What a great workout"))
        workouts.append((1, 1112323318, 2, 90, 698, 182, "I did great"))
        c.addAllWorkoutsDB(workouts)
        return "SUCCESS\n"
    elif request.method == 'DELETE':
        if 'u_id' in request.args:
            c.deleteAllWorkoutsDB(request.args['u_id'])
            return "SUCCESS\n"
        else:
            return "ERROR\n"


if __name__ == '__main__':
    app.debug = True
    app.run()


