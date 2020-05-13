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

import javaThread.procon.SharedObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

class SharedChattingObject{
	private static SharedChattingObject obj = new SharedChattingObject();
	private static Socket s;
	private static BufferedReader br;
	private static PrintWriter pr;

	private SharedChattingObject(){}
	
	public static SharedChattingObject getInstance() {
		return obj;
	}
		
	public synchronized void chatting(Socket s, BufferedReader br, PrintWriter pr) {
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


class ChattingRunnable implements Runnable {
	private SharedChattingObject obj;
	private Socket s;
	private static BufferedReader br;
	private static PrintWriter pr;

	public ChattingRunnable(SharedChattingObject obj, Socket s) {
		super();
		this.obj = obj;
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
		obj.chatting(s, br, pr);
	}
}

public class EXAM04_ChattingServer_made_me extends Application {
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
					SharedChattingObject obj = SharedChattingObject.getInstance();
					server = new ServerSocket(9999);
					while (true) {
						Socket s = server.accept();
						ChattingRunnable r = new ChattingRunnable(obj, s);
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
