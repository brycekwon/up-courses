import psycopg2

from flask import Flask, request, jsonify

app = Flask(__name__)

try:
    conn = psycopg2.connect("dbname=grafana user=brycekwon password=supersecret host=127.0.0.1 port=5432")
    conn.autocommit = True
    cur = conn.cursor()
except Exception as e:
    print(f"An error occurred: {e}")


@app.route("/", methods=["POST"])
def kill_me_please():
    try:
        data = request.get_json()
        print(data)

        x = data["x"]
        y = data["y"]

        try:
            cur.execute("INSERT INTO random_data (num1, num2) VALUES (%s, %s)", (x, y))

        except Exception as e:
            return jsonify(message=e), 500

        return jsonify(message="YAY!"), 200
    except Exception as e:
        return jsonify(message=e), 500


app.run(host="0.0.0.0", port=1313)
