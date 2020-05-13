package javaArduino;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class LEDBlinkServer extends Application {
	private TextArea ta;
	private Button btn;
	private ServerSocket server;
	private BufferedReader br;
	private BufferedWriter bw;
	private PrintWriter pr;
	
	private void printMSG(String msg) {
		Platform.runLater(() -> {
			ta.appendText(msg + "\n");
		});
	}

	@SuppressWarnings("unchecked ")
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500); // 가로, 세로 px 단위

		ta = new TextArea(); // 글상자를 생성
		root.setCenter(ta); // borderPane 가운데에 TextArea 부착

		btn = new Button("서버 기동");
		btn.setPrefSize(250, 50);
		btn.setOnAction(e -> {
				Runnable r = new Runnable() {
					public void run() {
						try {
							printMSG("서버 가동");
							server = new ServerSocket(1234);
							Socket s = server.accept();
							printMSG("클라이언트 접속");
							br = new BufferedReader(new InputStreamReader(s.getInputStream()));
							pr =  new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
							String msg = "";
							while(true){
								if((msg = br.readLine()) != null) {
									if(msg.equals("LED_ON")) {
										printMSG("on");
										bw.write(msg, 0, msg.length());
										bw.flush();
									}
									if(msg.equals("LED_OFF")) {
										printMSG("off");
										bw.write(msg, 0, msg.length());
										bw.flush();
									}
									if(msg.equals("LED_ON_ACK")) {
										printMSG("LED_ON_Ack");
										bw.write(msg, 0, msg.length());
										bw.flush();
									}
									if(msg.equals("LED_OFF_ACK")) {
										printMSG("LED_OFF_Ack");
										bw.write(msg, 0, msg.length());
										bw.flush();
									}
								}
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				};
				Thread t = new Thread(r);
				t.start();
		});

		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(btn); // FlowPane에 버튼을 부착

		root.setBottom(flowPane); // 전체화면에 아래부분에 FlowPane 부착

		Scene scene = new Scene(root); // borderPane을 포함하는 장명 생성
		primaryStage.setScene(scene); // windows의 화면을 scene으로 설정
		primaryStage.setTitle("예제용 JavaFX");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		primaryStage.show();
		
		CommPortIdentifier portIdentifier = null;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier("COM8");
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
					bw = new BufferedWriter(new OutputStreamWriter(out));
					br = new BufferedReader(new InputStreamReader(in));
					Runnable r2 =  new Runnable() {
						
						@Override
						public void run() {
							byte[] buffer = new byte[1024];
							int len = -1;
							try {
								while((len = in.read(buffer)) != -1) {
									System.out.print(new String(buffer, 0, len));
									String msg = new String(buffer, 0, len);
									System.out.println(msg);
									if(msg.equals("LED ON ACK")) {
										printMSG("LED_ON_Ack");
										bw.write(msg, 0, msg.length());
										bw.flush();
									}
									if(msg.equals("LED OFF ACK")) {
										printMSG("LED_OFF_Ack");
										bw.write(msg, 0, msg.length());
										bw.flush();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}};
					Thread t2 = new Thread(r2);
					t2.start();
					
					
				} else {
					System.out.println("serialport만 이용가능");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(); // 객체를 만들지 않더라도 start() method가 호출됨

	}

}
