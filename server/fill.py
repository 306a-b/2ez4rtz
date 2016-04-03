from flask import Flask, jsonify
import pickle
from flask_sqlalchemy import SQLAlchemy
from classes import User, Group, BePopular, db, app, Feed
import time
import vk
from vk_session import vk_session


db.drop_all()
db.create_all()
db.create_all(bind=['users', 'groups', 'feed', 'bepop'])


concrete_group_ids = [105870276, 99584926, 86236412, 55633888]


concrete_audios = []
api = vk.API(vk_session)
# token = vk.Session.get_access_token(session)
# user_info = api.users.get()
# group_ids = api.groups.get()
# user = User(user_info[0]['uid'], pickle.dumps(group_ids))
# db.session.add(user)
count = 0  # requests counter
images = []
for i in concrete_group_ids:
    search_query = api.groups.getById(group_id=str(i))[0]['name']
    try:
        search_query = str(search_query.split(' ')[0]) + ' ' + str(search_query.split(' ')[1])
    except:
        str(search_query.split(' ')[0])
    song = api.audio.search(q=search_query, performer_only=1, count=1)
    bepop = BePopular(i, pickle.dumps(song))
    db.session.add(bepop)
    audios = api.audio.search(q=search_query, performer_only=1, sort = 0, count=10)
    groups_info = api.groups.getById(group_id = i, count = 1, fields = ['members_count', 'contacts'])
    group = Group(i, pickle.dumps(groups_info))
    db.session.add(group)
    feed = Feed(i, pickle.dumps(audios))
    db.session.add(feed)
    count += 2
    if count % 4 == 0:
        time.sleep(3)


db.session.commit()
