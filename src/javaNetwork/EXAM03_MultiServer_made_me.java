package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

@SuppressWarnings("unchecked ")
public class EXAM03_MultiServer_made_me extends Application {
	private Button makeServerBtn, closeServerBtn;
	private TextArea ta;
	private Thread t;

	private void printMSG(String msg) {
		Platform.runLater(() -> {
			ta.appendText(msg + "\n");
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500); // 가로, 세로 px 단위

		ta = new TextArea(); // 글상자를 생성
		root.setCenter(ta); // borderPane 가운데에 TextArea 부착

		makeServerBtn = new Button("서버 만들기");
		makeServerBtn.setPrefSize(250, 50);
		makeServerBtn.setOnAction(e -> {
			ta.setText("서버가 만들어졌습니다.");
			SockerRunnable r = new SockerRunnable();
			t = new Thread(r);
			t.start();
			System.out.println("서버가 생성되었습니다.");

		});

		closeServerBtn = new Button("서버 닫기");
		closeServerBtn.setPrefSize(250, 50);
		closeServerBtn.setOnAction(e -> {
			t.interrupt();
			ta.setText("서버를 닫았습니다.");
		});

		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(makeServerBtn); // FlowPane에 버튼을 부착
		flowPane.getChildren().add(closeServerBtn); // FlowPane에 버튼을 부착

		root.setBottom(flowPane); // 전체화면에 아래부분에 FlowPane 부착

		Scene scene = new Scene(root); // borderPane을 포함하는 장명 생성
		primaryStage.setScene(scene); // windows의 화면을 scene으로 설정
		primaryStage.setTitle("Echo");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch();
//		try {
//			// 1. 서버프로그램이기 때문에 ServerSocket 필요
//			// 클라이언트가 접속할 수 있는 포트번호를 가지고 ServerSocket을 생성
//			ServerSocket server = new ServerSocket(7777);
//			System.out.println("서버가 생성되었습니다.");
//			// 2. 클라이언트가 접속할 수 있게 ServerSocket의 accept를 호출
//			while (true) {
//				Socket s = server.accept();
//				MultiServerRunnable r = new MultiServerRunnable(s, server);
//				Thread t = new Thread(r);
//				t.start();
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

}

class SockerRunnable implements Runnable {
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(7777);
			System.out.println("서버가 생성되었습니다.");
			try {
				while (true) {
					Socket s = server.accept();
					Thread.sleep(100);
					MultiServerRunnable r = new MultiServerRunnable(s, server);
					Thread t = new Thread(r);
					t.start();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class MultiServerRunnable implements Runnable {
	private String msg;
	private BufferedReader br;
	private PrintWriter pr;
	private Socket s;
	private ServerSocket server;

	public MultiServerRunnable(Socket s, ServerSocket server) {
		super();
		this.s = s;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pr = new PrintWriter(s.getOutputStream());

			while (true) {
				// client가 보내준 데이터 받음
				msg = br.readLine();
				if ((msg == null) || (msg.equals("@EXIT"))) {
					break;
				}
				pr.println(msg);
				pr.flush();
			}
			if (pr != null)
				pr.close();
			if (br != null)
				br.close();
			System.out.println("서버가 종료되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
