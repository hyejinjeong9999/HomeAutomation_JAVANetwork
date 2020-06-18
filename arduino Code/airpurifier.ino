#include <pm2008_i2c.h>

PM2008_I2C pm2008_i2c;

String APONOFF = "0";

int gasValue = 0;
int gasPin = 0;

int measurePin = 3;
int ledPower = 13;
int samplingTime = 280;
int deltaTime = 40;
int sleepTime = 9680;
float voMeasured = 0;
float calcVoltage = 0;
float dustDensity_temp = 0;
float dustDensity = 0;

float dust2p5 = 0;
float dust10 = 0;

int loop_cnt = 0;

void setup() {
  pm2008_i2c.begin();
  Serial.begin(9600);
  pm2008_i2c.command();
  delay(1000);
}

void loop()
{
  if(Serial.available()){
    String str = Serial.readStringUntil('\n');
    int j = 0;
    if(str.startsWith("OFF")){
      APONOFF = "OFF";
        delay(10);  
    }else if(str.startsWith("ON")){
        APONOFF = "ON";
        delay(10);
      }
    }
  if(APONOFF == "ON"){
    digitalWrite(7, HIGH);
  }else{
    digitalWrite(7, LOW);
  }
  uint8_t ret = pm2008_i2c.read();
  if (ret == 0) {
    dust2p5 = pm2008_i2c.pm2p5_grimm;
    dust10 = pm2008_i2c.pm10_grimm;
  }
  
  //digitalWrite(ledPower, LOW);
  //delayMicroseconds(samplingTime);
  //voMeasured = analogRead(measurePin);
  //delayMicroseconds(deltaTime);
  //digitalWrite(ledPower, HIGH);
  //delayMicroseconds(sleepTime);
  //calcVoltage = voMeasured * (5.0 / 1024.0);
  //dustDensity_temp = (0.17 * calcVoltage - 0.1) * 1000;
  //if(dustDensity_temp < 20) dustDensity_temp = 0;
  //dustDensity += dustDensity_temp;


  gasValue = analogRead(gasPin);
  

  loop_cnt++;
  if(loop_cnt == 10){
    //dustDensity = dustDensity/ 10;
    loop_cnt = 0;
   //gasValue = gasValue / 10;
    
    Serial.print("/AIRPURIFIER"); 
    Serial.print(" ");
    Serial.print("/airpurifierStatus:");
    Serial.print(APONOFF);
    Serial.print(" ");
    Serial.print("/dust25:");
    Serial.print(dust2p5);
    Serial.print(" ");
    Serial.print("/dust10:");
    Serial.print(dust10);
    Serial.print(" ");
    Serial.print("/gasStatus:");
    Serial.print(gasValue, DEC);
    Serial.print(" ");
    Serial.println();
  }
 delay(500);
}
