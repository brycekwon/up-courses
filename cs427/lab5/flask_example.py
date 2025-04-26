from flask import Flask, jsonify, render_template, request
from datetime import datetime

app = Flask(__name__, template_folder="www/")

@app.route('/sensor_dth', methods=['POST'])
def add_dht():
    try:
        data = request.get_json() 
        print(data)
        global temperature, humidity
        temperature = data['temperature']
        humidity = data['humidity']
        current_time = datetime.now()
        date_time = current_time.strftime("%Y-%m-%d %H:%M:%S")
        print(date_time)

        resp = jsonify(message="Data Received successfully!")
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)
        return jsonify(message="Data error"), 500

@app.route('/get_data')
def get_data():
    data = {'temperature': temperature, 
            'humidity': humidity}
    return jsonify(data)

@app.route('/')
def index():
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=3000 )

