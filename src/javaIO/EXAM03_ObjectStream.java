package javaIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//DOS모드로 작성
//HashMap에 데이터를 저장해서 이 데이터를 File애 저장

public class EXAM03_ObjectStream {
	public static void main(String[] args) {
		//1. 로직처리를 통해서 만들어진 데이터 구조를 준비
		//최종결과 데이터가 HashMap으로 만들어졌다고 가정
		Map<String, String> map = new HashMap<String, String>();
	
		map.put("1", "홍길동");
		map.put("2", "신사임당");
		map.put("3", "강감찬");
		map.put("4", "유관순");
		
		//이 정보를 파일에 저장
		File file = new File("asset/StringData.txt");
		//대표적인 출력 Stream : printWirter
		//대표적인 입력 Stream : bufferedReader
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//객체직렬화를 통해서 저장하기 원하는 객체를 Stream을 통해서 보낼수 있음
			oos.writeObject(map);
			oos.flush();
			oos.close();
			fos.close();
			//PrintWriter pr = new PrintWriter(file);
			//pr.println("소리없는아우성");
			//pr.flush();
			//pr.close();
			//단일 문자열과 같은 형태의 간단한 형태의 데이터를 보낼때는 문제없음
			//저장할 데이터가 자료구조형태로 되어있음
			//자료구조 형태가 복잡하면 데이터를 저장하기 쉽지 않음
			//객체자체를 파일을 저장할 수 있음
			//스트림을 통해 문자열이 아닌 객체자체를 전달하는 것이 가능
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
























