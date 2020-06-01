from redis import StrictRedis
from flask import Flask, request, render_template

app = Flask(__name__)
redis = StrictRedis(host='127.0.0.1', password='123456', db=1)

@app.route('/visualize')
def visualize():
    data = []
    with open('record.txt', 'rb') as f:
        content = f.read()
    lines = content.decode('utf-8', 'ignore').split('$eol$')
    for line in lines:
        fileds = line.split('::')
        data.append(fileds[0:2] + fileds[3:5])
    data = data[:-1]
    # data.sort(key=lambda item : item[-1], reverse=True)
    return render_template('index.html', data=data)

app.run('0.0.0.0', 8000, debug=True)