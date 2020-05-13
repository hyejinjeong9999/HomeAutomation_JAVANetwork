package javaArduino;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam04_ChangeLight extends Application  {
	private TextArea ta;
	private Button btn;
	private ServerSocket server;
	private BufferedReader br;
	private static BufferedReader br2;
	private BufferedWriter bw, bw2;
	private PrintWriter pr;
	private String ms;
	private SerialPort serialPort;

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
						pr = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
						String msg = null;
						while (true) {
							if ((msg = br.readLine()) != null) {
								System.out.println(msg);
								msg = msg.substring(6);
								printMSG("LED세기 : " + msg);
								pr.write(msg, 0, msg.length());
								pr.flush();
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
		////////////////////////////////////////////////////////////////////////

	}

	
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
					br2 = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
					serialPort.addEventListener(new SerialListener12312(br2));
					serialPort.notifyOnDataAvailable(true);
				} else {
					System.out.println("serialport만 이용가능");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		launch(); // 객체를 만들지 않더라도 start() method가 호출됨
		

	}
}

class SerialListener12312 implements SerialPortEventListener{
	private BufferedReader br;
	private String ms;
	
	public SerialListener12312(BufferedReader br) {
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
