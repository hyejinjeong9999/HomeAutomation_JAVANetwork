package javaArduino;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;


public class Exam03_reciveFromAndroid {
	static BufferedReader br;
	static PrintWriter pr;
	static ExecutorService executorService;
	static byte[] buffer;
	
	public static void main(String[] args) {
		try {
			executorService = Executors.newCachedThreadPool();
		
			ServerSocket server = new ServerSocket(1357);
			
			Socket s = server.accept();
			System.out.println("접속됨");
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter pr = new PrintWriter(s.getOutputStream());
			
			CommPortIdentifier portIdentifier = null;
			portIdentifier = CommPortIdentifier.getPortIdentifier("COM8");
			if(portIdentifier.isCurrentlyOwned()) {
				System.out.println("포트가 사용 중 입니다.");
			}else {
				CommPort commPort = portIdentifier.open("PORT_OPEN", 2000);
				
				if(commPort instanceof SerialPort) {
					SerialPort serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, 
							SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					InputStream in = serialPort.getInputStream();
					OutputStream out = serialPort.getOutputStream();
					
					Runnable r = new Runnable() {
						@Override
						public void run() {
							try {
								while(true) {
									String msg = br.readLine();
									if( (msg == null) || (msg.equals("@EXIT")) ) {
										break;
									}
									System.out.println(msg);
									buffer = msg.getBytes();
									out.write(buffer);
									out.flush();
								}
								if(pr != null) pr.close();
								if(br != null) br.close();
								if(s != null) s.close();
								if(server != null)server.close();
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					executorService.execute(r);

				}else {
					System.out.println("serialport만 이용가능");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		
	}
}
