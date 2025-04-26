#include <WiFi.h>
#include <PubSubClient.h>
#include <DHT11.h>

const char* ssid = "berryfarm";
const char* password = "circuits_are_hard";

const char* mqtt_server = "10.12.20.22";
const int mqtt_port = 1883;

const char* dht_topic = "dht_1";

DHT11 dht11(5);

WiFiClient espClient;
PubSubClient client(espClient);

void connectToWiFi() {
    Serial.print("Connecting to WiFi...");
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
        delay(1000);
        Serial.print(".");
    }
    Serial.println("\nConnected to WiFi!");
}

void connectToMQTT() {
    while (!client.connected()) {
        Serial.print("Connecting to MQTT...");
        if (client.connect("ESP32Client32423")) {
            Serial.println("Connected to MQTT broker!");
        } else {
            Serial.print("Failed, rc=");
            Serial.print(client.state());
            Serial.println(" Retrying in 5 seconds...");
            delay(5000);
        }
    }
}

void setup() {
    Serial.begin(9600);

    connectToWiFi();

    client.setServer(mqtt_server, mqtt_port);

    connectToMQTT();

}

void loop() {
    int temperature = 0;
    int humidity = 0;

    int result = dht11.readTemperatureHumidity(temperature, humidity);

    if (result != 0) {
        temperature = -1;
        humidity = -1;
    }

    String payload = "temperature=" + String(temperature) + "-humidity=" + String(humidity);

    client.publish(dht_topic, payload.c_str());

    Serial.println("Published data:");
    Serial.println(payload);

    delay(2000);
}
