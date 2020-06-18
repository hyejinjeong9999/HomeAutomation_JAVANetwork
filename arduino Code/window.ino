#include <Servo.h>

int on_value;
int off_value;
int on_pin = 9;
int off_pin = 7;
String windowStatus;

int loop_cnt = 0;

Servo servo1;
Servo servo2;
Servo servo3;
int servoStatus = 0;

int doorStatus = 0;
void setup() {
  Serial.begin(9600);
  pinMode(on_pin, INPUT);
  pinMode(off_pin, INPUT);
  pinMode(0, OUTPUT);
  servo1.attach(11);
  servo2.attach(10);
  servo3.attach(8);
}

void loop()
{ 
   if(Serial.available()){
    String str = Serial.readStringUntil('\n');
    int j = 0;
    if(str.startsWith("OFF")){
        servoStatus == 0;
        for(int i = 90; i>0;i--){
         j = 90- i; 
         servo1.write(i);
         servo2.write(j);
         delay(10);  
      }
    }else if(str.startsWith("ON")){
        servoStatus == 1;
        for(int i = 0; i<91;i++){
         j = 90 - i;
         servo1.write(i);
          servo2.write(j);
          delay(10);
      }
    }else if(str.startsWith("DOOR")){
        if(doorStatus == 0){
          for(int i = 0; i<91;i++){
            servo3.write(i);
            delay(10);
           }
           doorStatus = 1;
        }else if(doorStatus == 1){
          for(int i = 90; i>0;i--){
            servo3.write(i);
            delay(10);
           }
           doorStatus = 0;
        }
    }else if(str.startsWith("LIGHTON")){
      digitalWrite(0, HIGH);      
    }else if(str.startsWith("LIGHTOFF")){
      digitalWrite(0, LOW);      
    }
   }
   

  on_value = digitalRead(on_pin);
  off_value = digitalRead(off_pin);

  if(on_value == 0 && off_value == 1){
    windowStatus = "ON";
  }else if(on_value == 1 && off_value == 0){
    windowStatus = "OFF";
  }else{
    windowStatus = "ERROR";
  }

  loop_cnt++;
  if(loop_cnt == 10){
    loop_cnt = 0;
    
    Serial.print("/WINDOW");
    Serial.print(" ");
    Serial.print("/windowStatus:");
    Serial.print(windowStatus);
    Serial.print(" ");
    Serial.println();
  }
 delay(500);
}
