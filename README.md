
# RealTimeDetector
This is a Real-time temperature, humidity and gas measurement application based on Firebase with NodeMCU.
![resim1](https://i.hizliresim.com/yj29GL.jpg) ![resim2](https://i.hizliresim.com/6MYGDP.jpg)

Notification when toxic gas measured.       

![resim3](https://i.hizliresim.com/GGbXZ2.jpg)
## Before Start
### 1-Creating Firebase Account and Real Time Database 
You have to create your own Firebase account and new Project with a Real Time Database as seen below.
![resim4](https://i.hizliresim.com/jkbDSG.jpg)
### 2-NodeMCU
##### Setting Board
You can set your board with this schema,

![resim4](https://i.hizliresim.com/CJcy6M.jpg)

##### Editing Code
- First Install the esp8266 Board,

Arduino IDE -> File -> Preferences -> additional Boards Manager URLs   
https://arduino.esp8266.com/stable/package_esp8266com_index.json

Tools -> Board -> Boards Manager -> esp8266 -> install

- Now Choose NodeMCU from Boards
Tools -> Board -> esp8266 modules -> NodeMCU 1.0(ESP-12E Module)

- You will need
```sh
esp8266.h
firebase.h
firebase-arduino.h
dht11.h
and ArduinoJson.h (for firebase.h)
``` 
 You can download Firebase libraries at this link;
 https://github.com/FirebaseExtended/firebase-arduino/tree/master/src  
 Other libraries can be found in the library manager of the Arduino but do not forget the ArduinoJson should be v[5.13.1](https://github.com/bblanchon/ArduinoJson/tree/v5.13.1).

If you are done with libraries, change these lines of code with your settings
```sh
#define FIREBASE_HOST "YOUR_HOST_ADDR"
#define FIREBASE_AUTH "YOUR_AUTH_CODE"

#define WIFI_SSID "YOUR_WIFI"
#define WIFI_PASSWORD "YOUR_PASSWD"
```  
Firebase Host and Firebase Auth can be found
Project Settings->Service Accounts->Database Secrets in Firebase Console
### 3-Before Building Android App
This source does not include google-services.json file. You can easily create yours in Android Studio just follow,

Tools->Firebase->Real Time Database and set it with your own Firebase account. Don't forget to change variable names in code if you change them in Real Time database.

### 4-Notification
  
If you want to use the notifications, you can use RTD_json.
Please refer to https://firebase.google.com/docs/functions/get-started for use it.

### A friendly warning
After several weeks or months, If your application is running on NodeMCU but is not updated in the Android application, it is probably because the FirebaseSSL Fingerprint has changed. When this happens, please replace the FirebaseFingerprint value in FirebaseHttpClient.h with the new FirebaseSSL Fingerprint.


