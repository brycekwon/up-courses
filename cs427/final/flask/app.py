from flask import Flask, g, render_template
from flask_mqtt import Mqtt
from flask_socketio import SocketIO
import sqlite3


app = Flask(
    __name__,
    static_url_path="",
    static_folder="static",
)

app.config["SECRET_KEY"] = 'wow-this-is-super-secret'
app.config["MQTT_BROKER_URL"] = "localhost"
app.config["MQTT_BROKER_PORT"] = 1883
app.config["DATABASE_LOCATION"] = "./database.db"


socketio = SocketIO(app)
mqtt = Mqtt(app)


@socketio.on('connect')
def handle_socket_connect():
    print("client socket connected")

    try:
        with app.app_context():
            db = get_db()

            query = f"SELECT temperature, humidity FROM dht_1 ORDER BY id DESC LIMIT 1"
            cur = db.execute(query)
            result = cur.fetchone()

            dht_1_temp = result['temperature']
            dht_1_humid = result['humidity']

            query = f"SELECT temperature, humidity FROM dht_2 ORDER BY id DESC LIMIT 1"
            cur = db.execute(query)
            result = cur.fetchone()

            dht_2_temp = result['temperature']
            dht_2_humid = result['humidity']

            cur.close()
    except sqlite3.Error as e:
        print(f"Database error: {e}")
        return

    socketio.emit("response", f"1_{dht_1_temp}-{dht_1_humid}")
    socketio.emit("response", f"2_{dht_2_temp}-{dht_2_humid}")


@socketio.on('relay')
def handle_socket_message(data):
    x = int(data.split('-')[-1])
    y = int(data.split('-')[0])

    if x == 1:
        mqtt.publish("lights", f"OFF{y}")
    elif x == 0:
        mqtt.publish("lights", f"ON{y}")


@mqtt.on_connect()
def handle_mqtt_connect(client, userdata, flags, rc):
    mqtt.subscribe("dht_1")
    mqtt.subscribe("dht_2")
    print(f"Connected with status code {rc}")


@mqtt.on_message()
def handle_mqtt_message(client, userdata, message):
    data = dict(
        topic=message.topic,
        payload=message.payload.decode(),
    )

    parts = data['payload'].split('-')

    device = data['topic'].split('_')[-1]
    temp, humid = parts[0].split('=')[-1], parts[1].split('=')[-1]

    try:
        temp = int(temp)
        humid = int(humid)
    except ValueError:
        return

    try:
        with app.app_context():
            db = get_db()

            query = f"INSERT INTO dht_{device} (temperature, humidity) VALUES (?, ?)"
            cur = db.execute(query, (temp, humid))

            db.commit()

            cur.close()
    except sqlite3.Error as e:
        print(f"Database error: {e}")
        return

    socketio.emit("response", f"{device}_{temp}-{humid}")


def get_db():
    db = getattr(g, "_database", None)
    if db is None:
        db = g._database = sqlite3.connect(app.config.get("DATABASE_LOCATION"))

    db.row_factory = sqlite3.Row
    return db


@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, "_database", None)
    if db is not None:
        db.close()


def init_db():
    with app.app_context():
        db = get_db()
        with app.open_resource('schema.sql', mode='r') as f:
            db.cursor().executescript(f.read())
        db.commit()


@app.route('/')
def index():
    return render_template('index.html')


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3000)
