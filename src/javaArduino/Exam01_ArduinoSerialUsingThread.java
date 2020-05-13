package javaArduino;

import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Exam01_ArduinoSerialUsingThread {
	
	public static void main(String[] args) {
		CommPortIdentifier portIdentifier = null;
		try {
			//1. 시리얼통신을 하기위한 COM포트 설정
			portIdentifier = CommPortIdentifier.getPortIdentifier("COM8");
			//2. port가 사용되고 있는지 확인
			if(portIdentifier.isCurrentlyOwned()) {
				System.out.println("포트가 사용 중 입니다.");
			}else {
				CommPort commPort = portIdentifier.open("PORT_OPEN", 2000);
				
				//port객체를 얻어온 후 serial port를 이용해야함
				//port에는 parallelport도 존재
				if(commPort instanceof SerialPort) {
					SerialPort serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, 
							SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					//데이터 통신을 하기 위해서 stream을 연다
					InputStream in = serialPort.getInputStream();
					OutputStream out = serialPort.getOutputStream();
					
					//thread를 이용해서 Arduino로 부터 들어오는 데이터를 받는다
					Thread t = new Thread(() -> {
						byte[] buffer = new byte[1024];
						int len = -1;
						try {
							while((len = in.read(buffer)) != -1) {
								System.out.print("데이터 : " + new String(buffer, 0, len));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					t.start();
				} else {
					System.out.println("serialport만 이용가능");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
