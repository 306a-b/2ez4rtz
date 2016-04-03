from flask import Flask, jsonify, request
import json
from classes import User, Group, BePopular, Feed
import pickle
import vk_session


funny_urls = [  'http://cs627122.vk.me/v627122175/148e2/OX5JvsuJ0DM.jpg',
                'https://pp.vk.me/c622229/v622229202/5534e/CNcmvwVnXvI.jpg',
               'https://pp.vk.me/c622229/v622229841/52a62/YsEWz0ELGmQ.jpg',
                'https://pp.vk.me/c627727/v627727901/23677/I8KCQykTssI.jpg']
app = Flask(__name__)


@app.route('/')
def index():
    return '/feed, /token, /bepop, /groups'


@app.route('/token')
def token():
    return vk_session.token


@app.route('/bepop/put', methods = ['GET', 'PUT'])
def bepop_put():
    if request.method == 'PUT':
        bepop_new = request.get_json(force)
        print (bepop_new)
        bepop = BePopular()


@app.route('/feed')
def feed():
    al = Feed.query.all()
    answer = { 'feed' : []}
    for row in al:
        audios = pickle.loads(row.audio)
        answer['feed'].append({row.group_id : audios})
    return jsonify(answer)


@app.route('/groups')
def groups():
    al = Group.query.all()
    answer = { 'groups' : []}
    for row in al:
        groups_info = pickle.loads(row.groups_info)
        answer['groups'].append({row.group_id : groups_info})
    return jsonify(answer)


@app.route('/bepop')
def bepop_get():
    al = BePopular.query.all()
    answer = { 'bepopular' : []}
    for row in al:
        song = pickle.loads(row.audio)[1]
        for i in ['lyrics_id', 'owner_id']:
            del song[i]
        song['image_url'] = funny_url
        song['group_id'] = row.group_id
        answer['bepopular'].append(song)
    return jsonify(answer)

if __name__ == '__main__':
    app.run(host='10.25.3.190', port=1488, debug=True)
