package javaIO;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings("unchecked ")
public class EXAM02_Notepad extends Application{
	private TextArea ta;
	private Button openBtn, saveBtn;
	
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
		
		openBtn = new Button("파일 열기");	
		openBtn.setPrefSize(250, 50);
		openBtn.setOnAction(e -> {
				//1. textarea 초기화
				ta.clear();
				//2. open할 파일을 선택 file chooser를 이용
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Open할 파일을 선택해주세여");
				File file = chooser.showOpenDialog(primaryStage);
				try {
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					String line = "";
					while((line = br.readLine()) != null){
						printMSG(line);
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		);
		
		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(openBtn);	//FlowPane에 버튼을 부착
		//flowPane.getChildren().add(saveBtn);	//FlowPane에 버튼을 부착
		
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
		launch();	//객체를 만들지 않더라도 start() method가 호출됨

	}
		

}












