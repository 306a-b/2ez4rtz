from flask import Flask, jsonify
from flask.ext.restless import APIManager
from flask.ext.sqlalchemy import SQLAlchemy
import pickle
import vk
import time
from vk_session import vk_session


app = Flask(__name__)
app.config['DEBUG'] = True
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///main.db'
db = SQLAlchemy(app)
api = vk.API(vk_session)
concrete_group_ids = [41960330, 5844067, 99584926]


class User(db.Model):
    user_id = db.Column(db.Integer, primary_key=True)
    group_id = db.Column(db.PickleType)

    def __init__(self, user_id, group_id):
        self.user_id = user_id
        self.group_id = group_id

    def __repr__(self):
        return "{} : {}".format(self.user_id, pickle.loads(self.group_id))


class Group(db.Model):
    group_id = db.Column(db.Integer, primary_key=True)
    news = db.Column(db.PickleType)

    def __init__(self, group_id, news):
        self.group_id = group_id
        self.news = news

    def __repr__(self):
        return jsonify("{} : {}".format(self.group_id, pickle.loads(self.news)))


class be_popular(db.Model):
    group_id = db.Column(db.Integer, primary_key=True)
    audio = db.Column(db.PickleType)

    def __init__(self, group_id, audio):
        self.group_id = group_id
        self.audio = pickle.dumps(audio)


    def __repr__(self):
        #print(str((pickle.loads(self.audio))))
        #b''.decode()
        return "{} : {}".format(self.group_id, pickle.loads(self.audio, encoding='bytes')).decode('utf-8')


"""db.drop_all()
count = 0  # requests counter
for i in concrete_group_ids:
        search_query = api.groups.getById(group_id=str(i))[0]['name']
        try:
            search_query = str(search_query.split(' ')[0]) + ' ' + str(search_query.split(' ')[1])
        except:
            str(search_query.split(' ')[0])
        print(search_query)
        song = api.audio.search(q=search_query, performer_only=1, count=1)
        bepop = be_popular(i, pickle.dumps(song))
        db.session.add(bepop)
        count += 1
        if count % 4 == 0:
             time.sleep(5)"""

db.create_all()
#db.session.commit()

manager = APIManager(app, flask_sqlalchemy_db=db)

manager.create_api(User,  methods=['GET', 'POST', 'DELETE', 'PATCH'], collection_name = 'user')
manager.create_api(Group, methods=['GET', 'POST', 'DELETE', 'PATCH'], collection_name = 'group')
manager.create_api(be_popular, methods=['GET', 'POST'], collection_name = 'bepop')
app.run(host='10.25.3.190', port=1337)
