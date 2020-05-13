package javaThread;


import java.io.UncheckedIOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*
 * javaFX를 이용한 화면 UI생성
 * 1. Application을 상속해서 해당 class에 정의한다
 * 2. Application이 가지는 추상 method를 오버라이드한다
 * 3. 
 */
@SuppressWarnings("unchecked ")
public class EXAM00_JAVAFXUITemplate extends Application{
	private TextArea ta;
	private Button btn;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//화면을 구성하고 event 처리를 담당
		//기본 Layout 생성 => boardePane(동서남북으로 구성된) 생성
		BorderPane root = new BorderPane();
		//borderPan size설정
		root.setPrefSize(700, 500);	//가로, 세로 px 단위
		
		ta = new TextArea();	//글상자를 생성
		root.setCenter(ta);		//borderPane 가운데에 TextArea 부착
		
		btn = new Button("버튼 클릭");	
		btn.setPrefSize(250, 50);
		//버튼 클릭시 이벤트 처리, 안드로이드 처럼 setOnClickListener
		btn.setOnAction(e -> {
				//동기화가 일어나지 않아 잘못된 결과를 나타낼 수 있음
				//UI component를 직접 제어하는 방법은 좋지 않음, 아래처럼
				//ta.appendText("버튼이 클릭되었어요!!" + "\n");
				//thread를 이용해서 메세지를 출력하는 방식으로 이용해야함
				//UI component를 제어할 때 Thread를 이용해서 제어해야 동기화 문제 해결가능
				Platform.runLater( () -> {
						ta.appendText("버튼이 클릭되었어요!!" + "\n");
					}
				);
			}
		);
		
		//일반 Panel하나를 생성 => LinearLayout처럼 동작
		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(btn);	//FlowPane에 버튼을 부착
		
		root.setBottom(flowPane);	//전체화면에 아래부분에 FlowPane 부착
		
		//Scene(장면)이 필요함
		Scene scene = new Scene(root);	//borderPane을 포함하는 장명 생성
		primaryStage.setScene(scene);		//windows의 화면을 scene으로 설정
		primaryStage.setTitle("예제용 JavaFX");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		primaryStage.show();

		
	}

	public static void main(String[] args) {
		launch();	//객체를 만들지 않더라도 start() method가 호출됨

	}
		

}












