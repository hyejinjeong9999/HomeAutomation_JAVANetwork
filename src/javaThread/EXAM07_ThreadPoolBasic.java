package javaThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*
 * Pool 개념
 * JAVA는 필요한 객체를 생성(new) -> Heap영역에 메모리가 할당됨
 *	불필요한 메모리는 garbage collector가 제거함
 * 이런 방식은 과정에 시간이 많이 걸려 효율적이지 못함
 * 
 * 사용할 객체를 미리 많이 생성해서 모아둠 -> Pool이라고 불리는 공간에 모아둠
 * 필요할떄마다 Pool안에서 객체를 가져다 사용함.
 * 사용이 끝나면 Pool에 반납함.
 * 이런 방식은 처음 생성에만 오래걸리지만 나중에 효율적으로 사용가능
 */

@SuppressWarnings("unchecked ")
public class EXAM07_ThreadPoolBasic extends Application{
	private TextArea ta;
	private Button initBtn;				//thread pool 생성
	private Button startBtn;			//thread pool에서 thread를 가져다 사용하는 버튼
	private Button shutdownBtn;		//thread pool 종료
	private ExecutorService executorService;		//thread pool class
	
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
		
		initBtn = new Button("Thread Pool 생성");	
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(e -> {
				//executorService = Executors.newFixedThreadPool(5);
				executorService =  Executors.newCachedThreadPool();
				printMSG("Pool안에 Thread 갯수 : " + 
				((ThreadPoolExecutor)executorService).getPoolSize());
			}
		);
		startBtn = new Button("Thread 생성");	
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(e -> {
				//Thread Pool에서 Thread를 가져다가 사용하는 코드
				for(int i=0;i<10;i++) {
					//1. Runnable interface를 구현한 객체를 생성
					//2. Thread Pool을 이용해서 thread 생성
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							String msg = "Thread Pool안의 갯수 : " +
									((ThreadPoolExecutor)executorService).getPoolSize();
							msg += ", Thread name : " + Thread.currentThread().getName();
							printMSG(msg);
						}
					};
					// 이전에는 Thread t  = new Thread(runnable);
					// 			t.start();
					executorService.execute(runnable);
				}
			}
		);
		shutdownBtn = new Button("Thread Pool 종료");	
		shutdownBtn.setPrefSize(250, 50);
		shutdownBtn.setOnAction(e -> {
			executorService.shutdown();
			}
		);
		
		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(initBtn);	//FlowPane에 버튼을 부착
		flowPane.getChildren().add(startBtn);	//FlowPane에 버튼을 부착
		flowPane.getChildren().add(shutdownBtn);	//FlowPane에 버튼을 부착
		
		root.setBottom(flowPane);	//전체화면에 아래부분에 FlowPane 부착
		
		Scene scene = new Scene(root);	//borderPane을 포함하는 장명 생성
		primaryStage.setScene(scene);		//windows의 화면을 scene으로 설정
		primaryStage.setTitle("예제용 JavaFX");
		primaryStage.setOnCloseRequest(e -> {
			
		});
		primaryStage.show();

		
	}

	public static void main(String[] args) {
		launch();	//객체를 만들지 않더라도 start() method가 호출됨

	}
		

}












