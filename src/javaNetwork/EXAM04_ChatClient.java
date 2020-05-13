package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;



@SuppressWarnings("unchecked ")
public class EXAM04_ChatClient extends Application {
	private TextArea ta, ta2;
	private Button connbtn;
	private TextField tf;
	private Socket s;
	private BufferedReader br;
	private PrintWriter pr;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	class ReceiveRunnable implements Runnable {
		private BufferedReader br;

		public ReceiveRunnable(BufferedReader br) {
			super();
			this.br = br;
		}

		@Override
		public void run() {
			String msg = "";
			try {
				while (true) {
					msg = br.readLine();
					if (msg == null) {
						break;
					}
					printMSG(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

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
		ta2 = new TextArea(); // 글상자를 생성
		ta2.setPrefSize(100, 50);
		root.setCenter(ta); // borderPane 가운데에 TextArea 부착

		connbtn = new Button("서버에 접속");
		connbtn.setPrefSize(250, 50);
		connbtn.setOnAction(e -> {
			ta.clear();
			try {
				s = new Socket("localhost", 9999);
				printMSG("서버접속 성공");
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				pr = new PrintWriter(s.getOutputStream());
				tf.setDisable(false);
				ReceiveRunnable r = new ReceiveRunnable(br);
				executorService.execute(r);

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		tf = new TextField();
		tf.setPrefSize(300, 50);
		tf.setDisable(true);
		tf.setOnAction(e -> {
			String msg = tf.getText();
			pr.println(msg + "@" + ta2.getText());
			pr.flush();
			tf.clear();
			
		});

		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(connbtn); // FlowPane에 버튼을 부착
		flowPane.getChildren().add(ta2); // FlowPane에 버튼을 부착
		flowPane.getChildren().add(tf); // FlowPane에 버튼을 부착

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
		launch(); // 객체를 만들지 않더라도 start() method가 호출됨

	}

}
