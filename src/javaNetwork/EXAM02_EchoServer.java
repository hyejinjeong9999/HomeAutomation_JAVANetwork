package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EXAM02_EchoServer {
	static BufferedReader br;
	static PrintWriter pr;
	public static void main(String[] args) {
		try {
			//1. 서버프로그램이기 때문에 ServerSocket 필요
			//클라이언트가 접속할 수 있는 포트번호를 가지고 ServerSocket을 생성 
			ServerSocket server = new ServerSocket(7777);
			System.out.println("서버가 생성되었습니다.");
			//2. 클라이언트가 접속할 수 있게 ServerSocket의 accept를 호출
			
			Socket s = server.accept();
			
			//3. 클라이언트로 부터 데이터를 받고, 그대로 클라이언트에 될려줘야함
			BufferedReader br = 
					new BufferedReader(new InputStreamReader(s.getInputStream()));
			//데이터를 보내기 위해 PrintWirter이용
			PrintWriter pr = new PrintWriter(s.getOutputStream());
			String msg = "";
			while(true) {
				//client가 보내준 데이터 받음
				msg = br.readLine();
				if( (msg == null) || (msg.equals("@EXIT")) ) {
					break;
				}
				pr.println(msg);
				pr.flush();
			}
			if(pr != null) pr.close();
			if(br != null) br.close();
			if(s != null) s.close();
			if(server != null)server.close();
			System.out.println("서버가 종료되었습니다.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}












