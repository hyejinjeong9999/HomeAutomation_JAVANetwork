package javaArduino;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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

public class RxTxSample extends Application {
	private TextArea ta;
	private Button btn;
	private ServerSocket server;
	private SerialPort serialPort;
	TestVO vo = new TestVO();
	// private ObjectInputStream ois;
	private BufferedReader portBR;
	private PrintWriter portPW;
	private BufferedReader socketBR;
	private PrintWriter socketPR;
	// private BufferedWriter socketBW;

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
				@Override
				public void run() {
					try {
						printMSG("서버 가동");
						server = new ServerSocket(1357);
						// Socket s = new Socket("70.12.229.165", 1357); //test Latte
						Socket s = new Socket("70.12.60.98", 1357);
						printMSG("클라이언트 접속");
						CommPortIdentifier portIdentifier = null;
						try {
							portIdentifier = CommPortIdentifier.getPortIdentifier("COM5");
							if (portIdentifier.isCurrentlyOwned()) {
								System.out.println("포트가 사용 중 입니다.");
							} else {
								CommPort commPort = portIdentifier.open("PORT_OPEN", 2000);

								if (commPort instanceof SerialPort) {
									serialPort = (SerialPort) commPort;
									serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
											SerialPort.PARITY_NONE);

									portBR = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
									portPW = new PrintWriter(new OutputStreamWriter(serialPort.getOutputStream()));
								} else {
									System.out.println("serialport만 이용가능");
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
						socketBR = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));

						socketPR = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
						// socketBW = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

						serialPort.addEventListener(new SerialListener3(portBR, socketPR));
						// serialPort.addEventListener(new SerialListener3(portBR, socketBW));

						serialPort.notifyOnDataAvailable(true);
						socketPR.println("/ID:WINDOWS IN");
						socketPR.flush();
						String msg = "";
						while (true) {
							msg = socketBR.readLine();
							if (msg != null) {
								System.out.println("안드로이드에서 받고 아두이노에 보내는 메세지 : " + msg);
								printMSG(msg);
								portPW.println(msg);
								portPW.flush();
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
		primaryStage.setTitle("프로젝트 - 자바 네트워크 샘플");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch();

	}

}

class SerialListener3 implements SerialPortEventListener {
	private BufferedReader br;
	private PrintWriter pr;
	private String msg;

	public SerialListener3(BufferedReader br, PrintWriter pr) {
		super();
		this.br = br;
		this.pr = pr;
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				// 아두이노에서 받고 안드로이드에 보내는 메세지
				msg = br.readLine();
				// System.out.println(" 받고 안드로이드에 보내는 메세지 : " + msg);
				pr.println(msg);
				pr.flush();
				// PrintWriter로 되면 PrintWriter로 모두 변경
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
