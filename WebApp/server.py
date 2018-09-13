"""server.py

    Application server utilizing custom built API and Auth0's JWT
"""
import controller as c
import datetime, json, jwt
from flask import Flask, jsonify, make_response, request as req
from functools import wraps


#TODO needs user validation and code clean up
app = Flask('/')


def token_required(d):
    @wraps(d)
    def decorated(*args, **kwargs):
        if 'Authorization' in req.headers:
            token = req.headers.get('Authorization')[6:]
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
    if req.method == 'GET' and 'email' in req.args:
        user = c.getUserDB(req.args['email'])
        jdata = jsonify({user[0]: {'email': user[1], 'password': user[2], 'fname': user[3], 'lname': user[4]}})
        return make_response(jdata, 200)
    elif req.method == 'POST' and req.headers['Content-Type'] == 'application/json':
        data = req.json
        c.addUserDB((data['email'], data['password'], data['fname'], data['lname']))
        return make_response("HTTP/1.1 201 - Created\n", 201)
    elif req.method == 'DELETE' and 'email' in req.args:
        c.deleteUserDB(req.args['email'])
        return make_response("HTTP/1.1 200 - Ok\n", 200)
    else:
        return make_response("HTTP/1.1 400 - Bad Request\n", 400)

@app.route("/workouts", methods = ['GET', 'POST', 'DELETE'])
@token_required
def workouts():
    if req.method == 'GET' and 'u_id' in req.args:
        workouts = c.getAllWorkoutsDB(req.args.get('u_id'))
        return make_response(jsonify(workouts), 200)
    elif req.method == 'POST' and req.headers['Content-Type'] == 'application/json':
        workouts = []
        data = req.json
        for d in data:
            workouts.append((d['u_id'], d['date'], d['type'], d['duration'], d['calories'], d['distance'], d['notes']))
            c.addAllWorkoutsDB(workouts)
        return make_response("HTTP/1.1 201 - Created\n", 201)
    elif req.method == 'DELETE' and 'u_id' in req.args:
        c.deleteAllWorkoutsDB(req.args['u_id'])
        return make_response("HTTP/1.1 200 - Ok\n", 200)
    else:
        return make_response("HTTP/1.1 400 - Bad Request\n", 400)

@app.route("/signup", methods = ['POST'])
def signup():
    if req.method == 'POST' and req.headers['Content-Type'] == 'application/json' and \
            set(('email', 'password', 'fname', 'lname')) <= req.json[0].keys():
        data = req.json[0]
        token = jwt.encode({'user' : data['email'], 'exp' : datetime.datetime.utcnow() + datetime.timedelta(minutes=60)}, secret)
        #TODO save email, encrypted password, fname, lname
        return jsonify({'token': token.decode('UTF-8')})
    else:
        return make_response("HTTP/1.1 400 - Bad Request\n", 400)

def setup():
    c.setupDB()
    return


if __name__ == '__main__':
    setup()
    app.config.from_pyfile("flask.cfg", silent=False)
    secret = app.config.get("SECRET")
    app.run()


