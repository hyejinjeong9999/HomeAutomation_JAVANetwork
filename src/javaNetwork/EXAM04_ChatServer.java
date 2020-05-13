package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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

class ChatSharedObject {
	// thread에 의해서 공유되어야 하는 데이터
	// 모든 클라이언트에 대한 thread를 만들기위해 필요한 Runnable 객체를 저장
	List<ChatRunnable> clients = new ArrayList<ChatRunnable>();

	// 새로운 사용자가 접속했을 때 client 안에 새로운 사용자에 대한 Runnable객체를 저장 및 삭제
	public void add(ChatRunnable r) {
		clients.add(r);
	}

	public void remove(ChatRunnable r) {
		clients.remove(r);
	}

	// 클라이언트가 데이터를 보내줬을 때 채팅메시지를 broadcast하는 method
	public void broadcast(String msg, String nickname) {
		for (ChatRunnable client : clients) {
			client.getPr().println(nickname +  " : " +msg);
			client.getPr().flush();
		}
	}

}

class ChatRunnable implements Runnable {
	private Socket s;
	private BufferedReader br;
	private PrintWriter pr;
	private ChatSharedObject shared;

	public PrintWriter getPr() {
		return pr;
	}

	public ChatRunnable(Socket s, ChatSharedObject shared) {
		super();
		this.s = s;
		this.shared = shared;
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
					shared.remove(this);
					break;
				}
				StringTokenizer st = new StringTokenizer(line, "@");
				String content = st.nextToken();
				String nickname = st.nextToken();
				shared.broadcast(content, nickname);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class EXAM04_ChatServer extends Application {
	private TextArea ta;
	private Button startBtn, stopBtn;
	private ExecutorService executorService = Executors.newCachedThreadPool();

	private ServerSocket server;

	private ChatSharedObject shared = new ChatSharedObject();

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
						ChatRunnable r = new ChatRunnable(s, shared);
						shared.add(r);
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
