package javaThread;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

@SuppressWarnings("unchecked")
public class EXAM02_ThreadSleep extends Application{
	private TextArea ta;
	private Button btn;
	
	private void printMSG(String msg) {
		Platform.runLater(() -> {
			System.out.println(Thread.currentThread().getName());
			ta.appendText(msg + "\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(Thread.currentThread().getName());

		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);	//가로, 세로 px 단위
		
		ta = new TextArea();	//글상자를 생성
		root.setCenter(ta);		//borderPane 가운데에 TextArea 부착
		
		btn = new Button("버튼 클릭");	
		btn.setPrefSize(250, 50);
		//버튼을 누르면 Thread 5개를 생성
		//각 thread는 1초마다 잤다 깼다 하면서 숫자를 하나 출력
		btn.setOnAction(e -> {
				//5개의 Thread를 생성
				for(int i = 0; i<5; i++) {
					Thread t = new Thread(() -> {
						try {
							for(int k=0;k<3;k++) {
							Thread.sleep(1000);
							printMSG(k + "=" + Thread.currentThread().getName());
							}
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					});
					t.start();
				}
			}
		);
		
		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(btn);	//FlowPane에 버튼을 부착
		
		root.setBottom(flowPane);	//전체화면에 아래부분에 FlowPane 부착
		
		Scene scene = new Scene(root);	//borderPane을 포함하는 장명 생성
		primaryStage.setScene(scene);		//windows의 화면을 scene으로 설정
		primaryStage.setTitle("예제용 JavaFX");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		primaryStage.show();

		
	}

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
		launch();	//객체를 만들지 않더라도 start() method가 호출됨

	}
		

}























