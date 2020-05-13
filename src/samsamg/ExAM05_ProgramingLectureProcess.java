package samsamg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class ExAM05_ProgramingLectureProcess {
	static BufferedReader br;
	static StringTokenizer st;
	static double result;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		int TC = Integer.parseInt(br.readLine());
		for(int tc = 0; tc <TC; tc++) {
			st= new StringTokenizer(br.readLine(), " ");
			int total = Integer.parseInt(st.nextToken());
			int count = Integer.parseInt(st.nextToken());
			
			ArrayList<Integer> lecture = new ArrayList<Integer>();
			String score = br.readLine();
			st = new StringTokenizer(score, " ");
			for(int i = 0; i <total; i++) {
				lecture.add(Integer.parseInt(st.nextToken()));
			}
			
			Collections.sort(lecture);
			for(int i = 0; i <lecture.size(); i++) {
				System.out.println(lecture.get(i).toString());
			}
			
			
			for(int i = lecture.size() - count; i < lecture.size(); i++) {
				result = (result + Integer.parseInt((lecture.get(i).toString()))) / 2;
			}

System.out.println(result);			
			
			
			
			
			
			
		}
	}
}
