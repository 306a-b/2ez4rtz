from flask import Flask, jsonify
import pickle
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_BINDS'] = {
    'users': 'sqlite:///users.db',
    'groups': 'sqlite:///groups.db',
    'feed': 'sqlite:///feed.db',
    'bepop': 'sqlite:///bepop.db'}
db = SQLAlchemy(app)


class User(db.Model):
    __bind_key__ = 'users'
    user_id = db.Column(db.Integer, primary_key=True)
    group_id = db.Column(db.PickleType)

    def __init__(self, user_id, group_id):
        self.user_id = user_id
        self.group_id = group_id

    def __repr__(self):
        return "User's ID is: {}  \n His groups are: {}".format(self.user_id, pickle.loads(self.group_id))


class Group(db.Model):
    __bind_key__ = 'groups'
    group_id = db.Column(db.Integer, primary_key=True)
    groups_info = db.Column(db.PickleType)

    def __init__(self, group_id, groups_info):
        pass
        self.group_id = group_id
        self.groups_info = groups_info

    def __repr__(self):
        return jsonify("{} : {}".format(self.group_id, pickle.loads(self.groups_info)))


class Feed(db.Model):
    __bind_key__ = 'feed'
    group_id = db.Column(db.Integer, primary_key=True)
    audio = db.Column(db.PickleType)

    def __init__(self, group_id, audio):
        pass
        self.group_id = group_id
        self.audio = audio

    def __repr__(self):
        return jsonify("{} : {}".format(self.group_id, pickle.loads(self.audio)))


class BePopular(db.Model):
    __bind_key__ = 'bepop'
    group_id = db.Column(db.Integer, primary_key=True)
    audio = db.Column(db.PickleType)

    def __init__(self, group_id, audio):
        pass
        self.group_id = group_id
        self.audio = audio

    def __repr__(self):
        return jsonify("{} : {}".format(self.group_id, pickle.loads(self.audio)))
