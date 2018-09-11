"""DatabaseConnector.py

    Jeremy Kerby
    Database functionality for application
"""
import mysql.connector
import logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(filename)s - %(levelname)s - %(message)s')


DB_NAME = 'blah'
connection = mysql.connector.connect(option_files='mysql.cfg') # using non root with basic read and write
cursor = connection.cursor()


def setup():
    """Create database and tables if it does not exist"""
    try:
        logging.info("CREATING DATABASE > %s", DB_NAME)
        cursor.execute("CREATE DATABASE IF NOT EXISTS {} DEFAULT CHARACTER SET 'utf8'".format(DB_NAME))
    except mysql.connector.Error as err:
        if err.errno:
            logging.error(err.msg)

    try:
        connection.database = DB_NAME
    except mysql.connector.Error as err:
        if err.errno == errorcode.ER_BAD_DB_ERROR:
            create_database(cur)
            connection.database = DB_NAME
        else:
            logging.error(err)

    TABLES = {}
    TABLES['users'] = (
        "CREATE TABLE `users` ("
        "  `u_id` int(7) NOT NULL AUTO_INCREMENT,"
        "  `role` varchar(10) NOT NULL,"
        "  `email` varchar(320) NOT NULL UNIQUE,"
        "  `password` char(64) NOT NULL,"
        "  `fname` varchar(25) NOT NULL,"
        "  `lname` varchar(25) NOT NULL,"
        "  PRIMARY KEY (`u_id`)"
        ") ENGINE=InnoDB")
    TABLES['workouts'] = (
        "CREATE TABLE `workouts` ("
        "  `w_id` int(7) NOT NULL AUTO_INCREMENT,"
        "  `u_id` int(7) NOT NULL,"
        "  `date` int(11) unsigned NOT NULL,"
        "  `type` int(1) NOT NULL,"
        "  `duration` int(4) NOT NULL,"
        "  `calories` int(4) NOT NULL,"
        "  `distance` int(4) NOT NULL,"
        "  `notes` text NOT NULL,"
        "  PRIMARY KEY (`w_id`)"
        ") ENGINE=InnoDB")

    for name, cmd in TABLES.items():
        try:
            logging.info("CREATING TABLE > %s", name)
            cursor.execute(cmd)
        except mysql.connector.Error as err:
            if err.errno:
                logging.error(err.msg)
    return None

def addUser(user):
    """Add single user"""
    logging.info("ADDING USER > %s", user)
    # encrypt user pass
    command = ("INSERT INTO users (role, email, password, fname, lname) VALUES (%s, %s, SHA2(%s, 256), %s, %s)")
    logging.info("EXECUTING SQL: %s", command)

    try:
        cursor = connection.cursor()
        cursor.execute(command, user)
        connection.commit()
        cursor.close()
    except mysql.connector.Error as err:
        if err.errno:
            logging.error(err.msg)
            print(err.msg)
    return None

def getUser(email):
    """Return user data for given email"""
    print("GETTING USER DATA FOR > %s", email)
    command = ("SELECT role, email, password, fname, lname FROM users WHERE email='" + email + "'")
    print("EXECUTING SQL: %s", command)

    try:
        cursor = connection.cursor()
        cursor.execute(command)
    except mysql.connector.Error as err:
        if err.errno:
            print(err.msg)
    user = cursor.fetchone()
    cursor.close()
    return user # return tuple

def deleteUser(email):
    """Delete user with given email"""
    logging.info("DELETING USER DATA FOR > %s", email)
    command = ("DELETE FROM users WHERE email='" + email +"'")
    logging.info("EXECUTING SQL: %s", command)

    try:
        cursor = connection.cursor()
        cursor.execute(command)
        connection.commit()
        cursor.close()
    except mysql.connector.Error as err:
        if err.errno:
            logging.error(err.msg)
    return None

def addAllWorkouts(workouts):
    """Add all workouts"""
    logging.info("ADDING ALL WORKOUTS > %s", workouts)
    command = ("INSERT INTO workouts (u_id, date, type, duration, calories, distance, notes) VALUES (%s, %s, %s, %s, %s, %s, %s)")
    logging.info("EXECUTING SQL: %s", command)

    try:
        cursor = connection.cursor()
        cursor.executemany(command, workouts)
        connection.commit()
        cursor.close()
    except mysql.connector.Error as err:
        if err.errno:
            print(err.msg)
    return None

def getAllWorkouts(u_id):
    """Return all workout data for given user id"""
    logging.info("GETTING ALL WORKOUT DATA FOR > %s", u_id)
    command = ("SELECT w_id, date, type, duration, calories, distance, notes FROM workouts WHERE u_id=" + u_id)
    logging.info("EXECUTING SQL: %s", command)

    try:
        cursor = connection.cursor()
        cursor.execute(command)
    except mysql.connector.Error as err:
        if err.errno:
            print(logging.error(err.msg))
    workouts = {}
    for (w_id, w_date, w_type, w_duration, w_calories, w_distance, w_note) in cursor:
        workouts[w_id] = {"date": w_date, "type": w_type, "duration": w_duration, "calories": w_calories, "distance": w_distance, "note": w_note}
    cursor.close()
    return workouts # return dictionary

def deleteAllWorkouts(u_id):
    """Delete all workouts for given user id"""
    logging.info("DELETING ALL WORKOUTS FOR > %s", u_id)
    command = ("DELETE FROM workouts WHERE u_id=" + u_id)
    logging.info("EXECUTING SQL: %s", command)

    try:
        cursor = connection.cursor()
        cursor.execute(command)
        connection.commit()
        cursor.close()
    except mysql.connector.Error as err:
        if err.errno:
            logging.error(err.msg)
    return None


setup()
