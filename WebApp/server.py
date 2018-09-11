"""server.py

    Application server utilizing custom built API and Auth0's JWT
"""
from flask import Flask, jsonify, make_response, request
from functools import wraps
import datetime, json, jwt
import controller as c


#TODO build custom response/error types with @app.errorhandler
#TODO needs user validation yo


app = Flask('/')
appsecret = "blah" # this needs to go in app config

def token_required(d):
    @wraps(d)
    def decorated(*args, **kwargs):
        if 'token' in request.args:
            token = request.args.get('token')
            try:
                data = jwt.decode(token, appsecret)
            except:
                return "INCORRECT TOKEN"
        else:
            return "NO TOKEN\n"
        return d(*args, **kwargs)
    return decorated

@app.route("/user", methods = ['GET', 'POST', 'DELETE'])
@token_required
def api_user():
    if request.method == 'GET':
        if 'email' in request.args:
            user = c.getUserDB(request.args['email'])
            return jsonify({'user': user})
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
@token_required
def api_workouts():
    if request.method == 'GET':
        if 'u_id' in request.args:
            workouts = c.getAllWorkoutsDB(request.args.get('u_id'))
            return jsonify({'workouts': workouts})
        else:
            return "WORKOUT GET ERROR\n"
    elif request.method == 'POST':
        if request.headers['Content-Type'] == 'application/json':
            workouts = []
            data = request.json
            for d in data:
                workouts.append((d['u_id'], d['date'], d['type'], d['duration'], d['calories'], d['distance'], d['notes']))
            c.addAllWorkoutsDB(workouts)
            return "SUCCESS\n"
        else:
            return "ERROR\n"
    elif request.method == 'DELETE':
        if 'u_id' in request.args:
            c.deleteAllWorkoutsDB(request.args['u_id'])
            return "SUCCESS\n"
        else:
            return "ERROR\n"

@app.route("/signup", methods = ['POST'])
def api_signup():
    if request.method == 'POST':
        if request.headers['Content-Type'] == 'application/json':
            data = request.json
            if data['email'] and data['password']:
                token = jwt.encode({'user' : data['email'], 'exp' : datetime.datetime.utcnow() + datetime.timedelta(minutes=60)}, appsecret)
                # save email, password, token to db
                return jsonify({'token': token.decode('UTF-8')})
            else:
                return "ERROR\n"
        else:
            return "ERROR\n"


if __name__ == '__main__':
    app.debug = True
    app.run()


