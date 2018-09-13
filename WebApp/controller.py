"""controller.py

    Jeremy Kerby
"""
import logging
logging.basicConfig(level=logging.CRITICAL, format='%(asctime)s - %(filename)s - %(levelname)s - %(message)s')
import database_connector as db


"""Database Methods"""
def setupDB():
    db.setup()
    return

def addUserDB(user):
    db.addUser(user)
    return

def getUserDB(email):
    return db.getUser(email)

def deleteUserDB(email):
    db.deleteUser(email)
    return

def addAllWorkoutsDB(workouts):
    db.addAllWorkouts(workouts)
    return

def getAllWorkoutsDB(u_id):
    return db.getAllWorkouts(u_id)

def deleteAllWorkoutsDB(u_id):
    db.deleteAllWorkouts(u_id)
    return


