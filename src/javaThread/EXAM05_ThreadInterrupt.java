package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

@SuppressWarnings("unchecked ")
public class EXAM05_ThreadInterrupt extends Application{
	private TextArea ta;
	private Button startBtn, stopBtn;
	private Thread countThread;
	
	//TextArea에 문자열을 출력하기 위해 method를 만듬
	private void printMSG(String msg) {
		Platform.runLater( () -> {
			ta.appendText(msg + "\n");
		});
		
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);	//가로, 세로 px 단위
		
		ta = new TextArea();	//글상자를 생성
		root.setCenter(ta);		//borderPane 가운데에 TextArea 부착
		
		startBtn = new Button("Thread 시작");	
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(e -> {
				//클릭되면 Thread가 생성되서 동작시키는 코드가 나옴
					countThread = new Thread(()  -> {
						for(int i = 0; i<100; i++) {
							try {
								Thread.sleep(1000);
								//sleep이 수행되었을 때 해당 Thread가 interrupt가 발생하면 Exception발생
								printMSG(i + "값 출력");
							} catch (InterruptedException e2) {
								break;
							}
						}
					});
					//daemon thread는 자신을 파생시킨 thread와 생명주기를 공유
					//자신을 생성한 thread가 종료디면 자신도 종료ㅗ
					countThread.setDaemon(true);
					countThread.start();
			
			}
		);
		stopBtn = new Button("Thread 중지");	
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(e -> {
					//클릭되면 Thread가 중지되서 동작시키는 코드가 나옴
					countThread = new Thread(()  -> {
						//클릭되면  thread를 중지
						countThread.interrupt();
						//
				});
			}
		);
		
		
		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(startBtn);	//FlowPane에 버튼을 부착
		flowPane.getChildren().add(stopBtn);	//FlowPane에 버튼을 부착
		
		root.setBottom(flowPane);	//전체화면에 아래부분에 FlowPane 부착
		
		Scene scene = new Scene(root);	//borderPane을 포함하는 장명 생성
		primaryStage.setScene(scene);		//windows의 화면을 scene으로 설정
		primaryStage.setTitle("Thread Interrput 예제");
		primaryStage.setOnCloseRequest(e -> {
			//System.exit(0);//내부에 있는 모든 Thread를 강제종료
		});
		primaryStage.show();

		
	}

	public static void main(String[] args) {
		launch();	//객체를 만들지 않더라도 start() method가 호출됨

	}
		

}












