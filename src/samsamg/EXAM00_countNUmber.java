package samsamg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class EXAM00_countNUmber {
	static BufferedReader br;
	static BufferedWriter bw;
	static int a, b, c;
	static String result;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
//		a = Integer.parseInt(br.readLine());
//		b = Integer.parseInt(br.readLine());
//		c = Integer.parseInt(br.readLine());
		a = 150;
		b = 266;
		c = 427;
		
		result = "" + (a * b * c);
		int[] array = new int[10];
		
		for(int i = 0; i < result.length(); i++) {
			array[result.charAt(i)-'0']++;
		}
		
		for(int i = 0; i < 10; i++) {
			System.out.println(array[i]);
		}
		System.out.println(a*b*c);
		System.out.println(result.charAt(0));
		System.out.println(result.charAt(1));
		System.out.println(result.charAt(2));
		System.out.println(result.charAt(3));
		System.out.println(result.charAt(4));
		System.out.println(result.charAt(5));
		System.out.println(result.charAt(6));
	}

}
