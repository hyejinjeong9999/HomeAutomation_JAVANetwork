package javaNetwork;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/*
 * 서버쪽 프로그램
 * 클라이언트 접속하면 현재시간을 전송하는 프로그램
 */

public class EXAM01_DataServer {
	public static void main(String[] args) {
		//서버 프로그램은 클라이언트를 기다림
		//클라이언트의 sockek 접속을 기다리는 server socket이 필요함
		try {
			ServerSocket server = new ServerSocket(5556);
			System.out.println("서버가 생성되었어요");
			//클라이언트의 접속을 기다리는 method를 호출
			Socket s = server.accept();	//blocking method, 클라이언트가 접속할때까지 대기
			
			System.out.println("클라이언트가 접속되었요");
			//3. 소켓이 생성되면 데이터 입출력을 담당하는 stream을 생성
			String date = (new Date()).toLocaleString();	//현재시간
			PrintWriter out = new PrintWriter(s.getOutputStream());
			out.println(date);
			out.flush();
			out.close();
			
			s.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

