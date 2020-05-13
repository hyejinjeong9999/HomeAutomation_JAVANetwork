package samsamg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class EXAM02_OX {
	static BufferedReader br;
	static BufferedWriter bw;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int n = Integer.parseInt(br.readLine());
		String[] array = new String[n];
		int[] resultArray = new int[n];
		
		for(int i = 0; i <n ; i++) {
			array[i] = br.readLine();
		}
		
		int plus =1;
		for(int i = 0; i <n ; i++) {
			for(int j = 0; j <array[i].length(); j++) {
				if(array[i].charAt(j) == 'o') {
					resultArray[i] += plus;
					plus++;
				}else {
					plus =1;
					}
			}
			plus =1;
		}
		
		for(int i =0; i < n; i++) {
			System.out.println(resultArray[i]);
		}
		
		
	}

}
