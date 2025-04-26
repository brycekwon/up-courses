#include <WiFi.h>
#include <PubSubClient.h>

const char* ssid = "berryfarm";
const char* password = "circuits_are_hard";

const char* mqtt_server = "10.12.20.22";
const int mqtt_port = 1883;

const char* topic = "lights";

const int relay1Pin = 23;
const int relay2Pin = 22;

WiFiClient espClient;
PubSubClient mqttClient(espClient);

void callback(char* topic, byte* payload, unsigned int length) {
    String message = "";
    for (unsigned int i = 0; i < length; i++) {
        message += (char)payload[i];
    }
    Serial.print("Message received on topic ");
    Serial.print(topic);
    Serial.print(": ");
    Serial.println(message);

    if (message == "ON1") {
        digitalWrite(relay1Pin, LOW);
    } else if (message == "OFF1") {
        digitalWrite(relay1Pin, HIGH);
    } else if (message == "ON2") {
        digitalWrite(relay2Pin, LOW);
    } else if (message == "OFF2") {
        digitalWrite(relay2Pin, HIGH);
    }
}

void connectToMQTT() {
    while (!mqttClient.connected()) {
        Serial.print("Connecting to MQTT broker...");
        if (mqttClient.connect("ESP32Client")) {
            Serial.println("Connected to MQTT broker");
            mqttClient.subscribe(topic);
            Serial.print("Subscribed to topic: ");
            Serial.println(topic);
        } else {
            Serial.print("Failed to connect, rc=");
            Serial.print(mqttClient.state());
            Serial.println(" Retrying in 2 seconds...");
            delay(2000);
        }
    }
}

void setup() {
    Serial.begin(115200);

    pinMode(relay1Pin, OUTPUT);
    pinMode(relay2Pin, OUTPUT);
    digitalWrite(relay1Pin, HIGH);
    digitalWrite(relay2Pin, HIGH);

    Serial.print("Connecting to Wi-Fi...");
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
    Serial.println("\nConnected to Wi-Fi");

    mqttClient.setServer(mqtt_server, mqtt_port);
    mqttClient.setCallback(callback);

    connectToMQTT();
}

void loop() {
    if (!mqttClient.connected()) {
        connectToMQTT();
    }
    mqttClient.loop();
}
