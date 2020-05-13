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

class chatRoomVO {
	private String chatRoomName;
	private ArrayList<String> user;

	public chatRoomVO(String chatRoomName, ArrayList<String> user) {
		super();
		this.chatRoomName = chatRoomName;
		this.user = user;
	}

	public String getChatRoomName() {
		return chatRoomName;
	}

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	public ArrayList<String> getUser() {
		return user;
	}

	public void setUser(ArrayList<String> user) {
		this.user = user;
	}

}

class MultiChatSharedObject {
	// thread에 의해서 공유되어야 하는 데이터
	// 모든 클라이언트에 대한 thread를 만들기위해 필요한 Runnable 객체를 저장
	List<MultiChatRunnable> multiClients = new ArrayList<MultiChatRunnable>();
	ArrayList<chatRoomVO> chatVO = new ArrayList<chatRoomVO>();
	ArrayList<String> user;

	public MultiChatSharedObject() {
		super();
		ArrayList<String> user1 = new ArrayList<String>();
		ArrayList<String> user2 = new ArrayList<String>();
		ArrayList<String> user3 = new ArrayList<String>();
		ArrayList<String> user4 = new ArrayList<String>();
		chatVO.add(new chatRoomVO("공부방", user1));
		chatVO.add(new chatRoomVO("java방", user2));
		chatVO.add(new chatRoomVO("반려동물방", user3));
		chatVO.add(new chatRoomVO("놀이방방", user4));
	}

	// 새로운 사용자가 접속했을 때 client 안에 새로운 사용자에 대한 Runnable객체를 저장 및 삭제
	public void add(MultiChatRunnable r) {
		multiClients.add(r);
	}

	public void remove(MultiChatRunnable r) {
		multiClients.remove(r);
	}

	public void makeChatRoom(String RoomName) {
		ArrayList<String> user = new ArrayList<String>();
		chatVO.add(new chatRoomVO(RoomName, user));
		initialization();
	}

	public void initialization() {
		String result = "@SERVER,";
		for (int i = 0; i < chatVO.size(); i++) {
			result += (chatVO.get(i).getChatRoomName() + ",");
		}
		broadcast(result);
	}

	// 클라이언트가 데이터를 보내줬을 때 채팅메시지를 broadcast하는 method
	public void broadcast(String msg) {
		for (MultiChatRunnable client : multiClients) {
			client.getPr().println(msg);
			client.getPr().flush();
		}
	}
	
	public void chatting(String msg) {
		StringTokenizer st = new StringTokenizer(msg, ",");
		String RoomName = st.nextToken();
		String userID = st.nextToken();
		String content = st.nextToken();
		for (MultiChatRunnable client : multiClients) {
			client.getPr().println("@CHATTING," + RoomName + "," +userID +"," +  content);
			client.getPr().flush();
		}
	}

	public void inUser(String chatNameUserID) {
		StringTokenizer st = new StringTokenizer(chatNameUserID, ",");
		String RoomName = st.nextToken();
		String userID = st.nextToken();
		int where = 0;
		for (int i = 0; i < chatVO.size(); i++) {
			if (chatVO.get(i).getChatRoomName().equals(RoomName)) {
				where = i;
			}
		}
		chatVO.get(where).getUser().add(userID);
		String result = "@USERIN," + RoomName +",";
		for (int i = 0; i < chatVO.get(where).getUser().size(); i++) {
			result += (chatVO.get(where).getUser().get(i) + ",");
		}
		System.out.println(result);
		broadcast(result);

	}
	
	public void outUser(String chatNameUserID) {
		StringTokenizer st = new StringTokenizer(chatNameUserID, ",");
		String RoomName = st.nextToken();
		String userID = st.nextToken();
		int where = 0;
		for (int i = 0; i < chatVO.size(); i++) {
			if (chatVO.get(i).getChatRoomName().equals(RoomName)) {
				where = i;
			}
		}
		chatVO.get(where).getUser().remove(userID);

		String result = "@USEROUT," + RoomName + ",";
		for (int i = 0; i < chatVO.get(where).getUser().size(); i++) {
			result += (chatVO.get(where).getUser().get(i) + ",");
		}
		System.out.println(result);
		broadcast(result);
	}

}

class MultiChatRunnable implements Runnable {
	private Socket s;
	private BufferedReader br;
	private PrintWriter pr;
	private MultiChatSharedObject shared;

	public PrintWriter getPr() {
		return pr;
	}

	public MultiChatRunnable(Socket s, MultiChatSharedObject shared) {
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
	public synchronized void run() {
		try {
			String line = "";
			while ((line = br.readLine()) != null) {
				if ((line.equals("@EXIT"))) {
					shared.remove(this);
					break;
				} else if (line.equals("@SERVER")) {
					shared.initialization();
				} else if (line.contains("@SERVER:")) {
					line = line.replace("@SERVER:", "");
					shared.makeChatRoom(line);
				} else if (line.contains("@USERIN:")) {
					line = line.replace("@USERIN:", "");
					shared.inUser(line);
				} else if (line.contains("@USEROUT:")) {
					line = line.replace("@USEROUT:", "");
					shared.outUser(line);
				} else if (line.contains("@CHATTING:")) {
					line = line.replace("@CHATTING:", "");
					shared.chatting(line);
				} else {
					shared.broadcast(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class EXAM05_MultiRoomChatServer extends Application {
	private TextArea ta;
	private Button startBtn, stopBtn;
	private ExecutorService executorService = Executors.newCachedThreadPool();

	private ServerSocket server;

	private MultiChatSharedObject shared = new MultiChatSharedObject();

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
						MultiChatRunnable r = new MultiChatRunnable(s, shared);
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
			try {
				server.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

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
