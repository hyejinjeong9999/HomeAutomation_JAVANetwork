package javaArduino;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

class SerialListener2 implements SerialPortEventListener{
	private BufferedReader br;
	private String ms;
	
	public SerialListener2(BufferedReader br) {
		super();
		this.br = br;
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				ms = br.readLine();
				System.out.println(ms);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}

public class Exam02_ArduinoSerialUsingEvent2 {
	private static BufferedReader br;
	public static void main(String[] args) {
		CommPortIdentifier portIdentifier = null;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier("COM11");
			if(portIdentifier.isCurrentlyOwned()) {
				System.out.println("포트가 사용 중 입니다.");
			}else {
				CommPort commPort = portIdentifier.open("PORT_OPEN", 2000);
				
				if(commPort instanceof SerialPort) {
					SerialPort serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, 
							SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					//데이터 통신을 하기 위해서 stream을 연다
					InputStream in = serialPort.getInputStream();
					OutputStream out = serialPort.getOutputStream();
					br = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
					serialPort.addEventListener(new SerialListener2(br));
					serialPort.notifyOnDataAvailable(true);
				} else {
					System.out.println("serialport만 이용가능");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
