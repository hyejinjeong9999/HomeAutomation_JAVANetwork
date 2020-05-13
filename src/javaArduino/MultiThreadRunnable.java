package javaArduino;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiThreadRunnable implements Runnable {
	Socket socket;
	BufferedReader bufferedReader;
	PrintWriter printWriter;
	SharedObject sharedObject;

	//Construction injection
	// Constructor - Socket과 공용객체를 답아와 초기화 해준다
	public MultiThreadRunnable(Socket socket, SharedObject sharedObject) {
		this.socket = socket;
		this.sharedObject = sharedObject;
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.printWriter = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// Client 로부터 넘어온 'MSG' 를 판별하여 기능 실행
		String msg = "";
		try {
			while ((msg = bufferedReader.readLine()) != null) {
//				msg = bufferedReader.readLine();
				System.out.println(msg);
				// /EXIT로 시작하는 메시지를 수신하면, DisRoomConnection
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}



}
