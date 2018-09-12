"""server.py

    Application server utilizing custom built API and Auth0's JWT
"""
import controller as c
import datetime, json, jwt
from flask import Flask, jsonify, make_response, request
from functools import wraps


#TODO needs user validation and code clean up
app = Flask('/')


def token_required(d):
    @wraps(d)
    def decorated(*args, **kwargs):
        if 'Authorization' in request.headers:
            #token = request.args.get('token')
            token = request.headers.get('Authorization')[6:]
            try:
                data = jwt.decode(token, secret)
            except:
                return make_response("HTTP/1.1 401 - Unauthorized\n", 401)
        else:
            return make_response("HTTP/1.1 401 - Unauthorized\n", 401)
        return d(*args, **kwargs)
    return decorated

@app.route("/user", methods = ['GET', 'POST', 'DELETE'])
@token_required
def user():
    if request.method == 'GET':
        if 'email' in request.args:
            user = c.getUserDB(request.args['email'])
            jdata = jsonify({user[0]: {'email': user[1], 'password': user[2], 'fname': user[3], 'lname': user[4]}})
            return make_response(jdata, 200)
        else:
            return make_response("HTTP/1.1 400 - Bad Request\n", 400)

    elif request.method == 'POST':
        if request.headers['Content-Type'] == 'application/json':
            data = request.json
            c.addUserDB((data['email'], data['password'], data['fname'], data['lname']))
            return make_response("HTTP/1.1 201 - Created\n", 201)
        else:
            return make_response("HTTP/1.1 400 - Bad Request\n", 400)

    elif request.method == 'DELETE':
        if 'email' in request.args:
            c.deleteUserDB(request.args['email'])
            return make_response("HTTP/1.1 200 - Ok\n", 200)
        else:
            return make_response("HTTP/1.1 400 - Bad Request\n", 400)
    else:
        return make_response("HTTP/1.1 405 - Method Not Allowed\n", 405)

@app.route("/workouts", methods = ['GET', 'POST', 'DELETE'])
@token_required
def workouts():
    if request.method == 'GET':
        if 'u_id' in request.args:
            workouts = c.getAllWorkoutsDB(request.args.get('u_id'))
            return make_response(jsonify(workouts), 200)
        else:
            return make_response("HTTP/1.1 400 - Bad Request\n", 400)

    elif request.method == 'POST':
        if request.headers['Content-Type'] == 'application/json':
            workouts = []
            data = request.json
            for d in data:
                workouts.append((d['u_id'], d['date'], d['type'], d['duration'], d['calories'], d['distance'], d['notes']))
            c.addAllWorkoutsDB(workouts)
            return make_response("HTTP/1.1 201 - Created\n", 201)
        else:
            return make_response("HTTP/1.1 400 - Bad Request\n", 400)

    elif request.method == 'DELETE':
        if 'u_id' in request.args:
            c.deleteAllWorkoutsDB(request.args['u_id'])
            return make_response("HTTP/1.1 200 - Ok\n", 200)
        else:
            return make_response("HTTP/1.1 400 - Bad Request\n", 400)
    else:
        return make_response("HTTP/1.1 405 - Method Not Allowed\n", 405)

@app.route("/signup", methods = ['POST'])
def signup():
    if request.method == 'POST':
        if request.headers['Content-Type'] == 'application/json':
            data = request.json
            if data[0]['email'] and data[0]['password']:
                token = jwt.encode({'user' : data[0]['email'], 'exp' : datetime.datetime.utcnow() + datetime.timedelta(minutes=60)}, secret)
                # save email, password, token to db
                return jsonify({'token': token.decode('UTF-8')})
            else:
                return make_response("HTTP/1.1 400 - Bad Request\n", 400)
        else:
            return make_response("HTTP/1.1 400 - Bad Request\n", 400)
    else:
        return make_response("HTTP/1.1 405 - Method Not Allowed\n", 405)


if __name__ == '__main__':
    app.config.from_pyfile("flask.cfg", silent=False)
    secret = app.config.get("SECRET")
    app.run()


