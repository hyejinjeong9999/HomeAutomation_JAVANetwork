package javaIO;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * java io
 * stream을 이용해서 처리
 * stream 데이터를 받아들이고 보낼 수 있는 통로
 * 
 * 데이터 연결통로는 단방향
 * InputStream : 입력스트림
 * OutputStream : 출력스트림
 * 이 두 종류의 Stream은 성능이 좋지 않음
 * 
 * Stream을 결합해서 더 좋은 스트림을 만들 수 있음
 */
public class EXAM01_keyboardInput {
	
	public static void main(String[] args) {
		System.out.println("소리없는아우성");
		//system.out : 도스창에 연결된 우리에게 미리 제공된 stream
		
		//도스창에 문자열을 입력받고 싶으면
		//기본적으로 InputStream이 있어야 데이터를 받을 수 있음
		//system.in : 도스창과 연결된 inputStream
		//InputStream은 효율이 안좋고, 문자열을 읽기에 좋지 못함
		//InputStream을 문자열 입력받기 좋은 InputStreamReader로 변환
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		//bufferedReader를 사용하면 readLine() method를 이용할 수 있음
		try {
			String msg = br.readLine();
			System.out.println("입력한 문자열 : " + msg);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
