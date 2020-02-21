#include <ESP8266WiFi.h>
#include <Firebase.h>                                                    
#include <FirebaseArduino.h>                                                
#include "DHT.h"                                                      

#define FIREBASE_HOST "YOUR_HOST_ADDR"                          
#define FIREBASE_AUTH "YOUR_AUTH_CODE"            

#define DHTPIN D4
#define WIFI_SSID "YOUR_WIFI"                                             
#define WIFI_PASSWORD "YOUR_PASSWD"                                  

int gasPin = D7;
DHT dht(DHTPIN, DHT11);

void setup() {
  
  Serial.begin(9600);
  delay(1000);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to ");                                     
  Serial.print(WIFI_SSID);

  while (WiFi.status() != WL_CONNECTED) {
    
    Serial.print(".");
    delay(500);
    
  }
  
  Serial.println();
  Serial.print("Connected to "); Serial.println(WIFI_SSID);
  Serial.println("IP Address : "); Serial.print(" " + WiFi.localIP());                                         
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  dht.begin();
                                                                
}

void loop() {
  
  float h = dht.readHumidity(); 
  float t = dht.readTemperature();
  int g = digitalRead(gasPin);                                       

  if (isnan(t) || isnan(h)|| isnan(g)) {
                                      
    Serial.println(F("Unreadlable data"));
    return;
    
  }
  
  Serial.print("Humidity: ");  Serial.print(h);
  Serial.print("%  Temperature: ");  Serial.print(t);  Serial.println(" °C ");
  Serial.print(" Gas: ");  Serial.print(g);
  Serial.println();                                                   
  delay(4000);
  
  Firebase.setFloat("/DHT11/Humidity", h);                          
  Firebase.setFloat("/DHT11/Temperature", t);
  Firebase.setInt("/MQ2",g);
  
}
