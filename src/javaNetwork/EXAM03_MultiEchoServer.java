package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

class EchoRunnable implements Runnable {
	private Socket s;
	private BufferedReader br;
	private PrintWriter pr;

	public EchoRunnable(Socket s) {
		super();
		this.s = s;
		try {
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.pr = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {
			String line = "";
			while ((line = br.readLine()) != null) {
				if ((line.equals("@EXIT"))) {
					break;
				}
				pr.println(line);
				pr.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class EXAM03_MultiEchoServer extends Application {
	private TextArea ta;
	private Button startBtn, stopBtn;
	private ExecutorService executorService = Executors.newCachedThreadPool();

	private ServerSocket server;

	private void printMSG(String msg) {
		Platform.runLater(() -> {
			ta.appendText(msg + "\n");
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);

		ta = new TextArea();
		root.setCenter(ta);

		startBtn = new Button("Echo Server on");
		startBtn.setPrefSize(150, 40);
		startBtn.setOnAction((e) -> {
			printMSG("[서버시작]");
			Runnable runnable = () -> {
				try {
					server = new ServerSocket(9999);
					while (true) {
						Socket s = server.accept();
						EchoRunnable r = new EchoRunnable(s);
						executorService.execute(r);
						printMSG("[새로운 클라이언트 접속]");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			};
			executorService.execute(runnable);
		});

		stopBtn = new Button("Echo Server off");
		stopBtn.setPrefSize(150, 40);
		stopBtn.setOnAction((e) -> {

		});

		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 40);
		flowPane.setPadding(new Insets(10, 10, 10, 10));
		flowPane.setHgap(10);
		flowPane.getChildren().add(startBtn);
		flowPane.getChildren().add(stopBtn);

		root.setBottom(flowPane);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Multi Echo Server");
		primaryStage.setOnCloseRequest(e -> {

		});
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch();

	}
}
