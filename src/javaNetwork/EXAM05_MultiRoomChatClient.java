package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EXAM05_MultiRoomChatClient extends Application {

	private String userID;
	private TextArea textArea;
	private Button connBtn;
	private Button disconnBtn;
	private Button createRoomBtn;
	private Button connRoomBtn;
	private Button disconnRoomBtn;
	private ListView<String> roomListView;
	private ListView<String> participantsListView;
	private FlowPane menuFlowPane;

	private Socket s;
	private BufferedReader br;
	private PrintWriter pr;
	private ExecutorService executorService = Executors.newCachedThreadPool();

	private static String roomName = "";

	private boolean flag = false;

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
					} else if (msg.contains("@SERVER,")) {
						msg = msg.replace("@SERVER,", "");
						msg = msg.substring(0, msg.length() - 1);
						StringTokenizer st = new StringTokenizer(msg, ",");
						Platform.runLater(() -> {
							roomListView.getItems().clear();
							while (st.hasMoreTokens()) {
								roomListView.getItems().add(st.nextToken());
							}
						});
					} else if (msg.contains("@USERIN,")) {
						msg = msg.replace("@USERIN,", "");
						msg = msg.substring(0, msg.length() - 1);
						StringTokenizer st = new StringTokenizer(msg, ",");
						if (st.nextToken().equals(roomName)) {
							Platform.runLater(() -> {
								participantsListView.getItems().clear();
								while (st.hasMoreTokens()) {
									participantsListView.getItems().add(st.nextToken());
								}
							});
						}
					} else if (msg.contains("@USEROUT,")) {
						msg = msg.replace("@USEROUT,", "");
						msg = msg.substring(0, msg.length() - 1);
						StringTokenizer st = new StringTokenizer(msg, ",");
						if (st.nextToken().equals(roomName)) {
							Platform.runLater(() -> {
								participantsListView.getItems().clear();
								while (st.hasMoreTokens()) {
									participantsListView.getItems().add(st.nextToken());
								}
							});
						}
					} else if (msg.contains("@CHATTING,")) {
						msg = msg.replace("@CHATTING,", "");
						StringTokenizer st = new StringTokenizer(msg, ",");
						if (st.nextToken().equals(roomName)) {
							String id = st.nextToken();
							printMSG(id + " : " + st.nextToken());
						}
					} else {
						printMSG(msg);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void printMSG(String msg) {
		Platform.runLater(() -> {
			textArea.appendText(msg + "\n");
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//////////////////////////////////// 화면 중앙
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);

		textArea = new TextArea();
		textArea.setEditable(false); // 임의로 채팅화면에 쓸 수 없도록
		root.setCenter(textArea);

		//////////////////////////////////// 화면 우측
		roomListView = new ListView<String>();
		participantsListView = new ListView<String>();

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(10);
		gridPane.add(roomListView, 0, 0);
		gridPane.add(participantsListView, 0, 1);
		root.setRight(gridPane);

		//////////////////////////////////// 버튼
		connBtn = new Button("chat 서버접속");
		connBtn.setPrefSize(150, 40);
		connBtn.setOnAction(e -> {
			if (!flag) {
				flag = true;
				Dialog<String> dialog = new TextInputDialog("닉네임을 입력하세요");
				dialog.setTitle("닉네임 설정");
				dialog.setHeaderText("닉네임 설정입니다. 적절한 이름을 입력하세요");
				Optional<String> result = dialog.showAndWait();
				String entered = "";
				if (result.isPresent()) {
					entered = result.get();
				}
				try {
					s = new Socket("localhost", 9999);
					printMSG("서버접속 성공");
					br = new BufferedReader(new InputStreamReader(s.getInputStream()));
					pr = new PrintWriter(s.getOutputStream());
					printMSG("채팅서버에 접속했습니다");
					printMSG(entered + " 님 환영합니다");
					userID = entered;
					ReceiveRunnable r = new ReceiveRunnable(br);
					executorService.execute(r);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				pr.println("@SERVER");
				pr.flush();
			} else printMSG("이미 접속되었습니다.");
		});

		createRoomBtn = new Button("채팅방 생성");
		createRoomBtn.setPrefSize(150, 40);
		createRoomBtn.setOnAction(e -> {
			Dialog<String> dialog = new TextInputDialog("생성할 방 이름을 입력하세요");
			dialog.setTitle("채팅방 생성");
			dialog.setHeaderText("채팅방 생성입니다. 적절한 이름을 입력하세요");
			Optional<String> result = dialog.showAndWait();
			String entered = "";
			if (result.isPresent()) {
				entered = result.get();
			} else
				return;
			// 방 이름이 서버에 전달이 되어야 함

			pr.println("@SERVER:" + entered); // 다시 업데이트된 목록을 서버에서 받기 위해
			pr.flush();

		});

		connRoomBtn = new Button("채팅방 접속");
		connRoomBtn.setPrefSize(150, 40);
		connRoomBtn.setOnAction(e -> {
			roomName = roomListView.getSelectionModel().getSelectedItem();
			textArea.clear();
			printMSG(roomName + " 에 " + userID + " 님이 입장했습니다");
			pr.println("@USERIN:" + roomName + "," + userID);
			pr.flush();

			FlowPane inputFlowPane = new FlowPane();
			inputFlowPane.setPadding(new Insets(10, 10, 10, 10));
			inputFlowPane.setPrefSize(700, 40);
			inputFlowPane.setHgap(10);

			TextField inputTF = new TextField();
			inputTF.setPrefSize(400, 40);
			inputTF.setDisable(false);
			inputTF.setOnAction(k -> {
				String msg = inputTF.getText();
				System.out.println("입력한 메세지는 : " + msg);
				if (!(msg.equals(""))) {
					pr.println("@CHATTING:" + roomName + "," + userID + "," + msg);
					pr.flush();
					inputTF.clear();
				}
			});
			disconnRoomBtn = new Button("채팅방 나가기");
			disconnRoomBtn.setPrefSize(150, 40);
			disconnRoomBtn.setOnAction(v -> {
				textArea.clear();
				root.setBottom(menuFlowPane);
				pr.println("@USEROUT:" + roomName + "," + userID);
				roomName = "";
				pr.flush();
				pr.println("@SERVER");
				pr.flush();
				participantsListView.getItems().clear();
			});

			inputFlowPane.getChildren().add(inputTF);
			inputFlowPane.getChildren().add(disconnRoomBtn);
			root.setBottom(inputFlowPane);

		});

		menuFlowPane = new FlowPane();
		menuFlowPane.setPadding(new Insets(10, 10, 10, 10));
		menuFlowPane.setPrefSize(700, 40);
		menuFlowPane.setHgap(10);
		menuFlowPane.getChildren().add(connBtn);
		menuFlowPane.getChildren().add(createRoomBtn);
		menuFlowPane.getChildren().add(connRoomBtn);

		root.setBottom(menuFlowPane);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Multi Room Chat Client");
		primaryStage.setOnCloseRequest(e -> {
			pr.println("@USEROUT:" + roomName + "," + userID);
			pr.flush();
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch();
	}
}
