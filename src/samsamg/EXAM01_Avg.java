package samsamg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


public class EXAM01_Avg {
	static BufferedReader br;
	static BufferedWriter bw;
	static int n;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));

		n = Integer.parseInt(br.readLine());
		String grade =br.readLine();
		StringTokenizer st = new StringTokenizer(grade, " ");
		double[] grades = new double[n];
		
		double max = 0;
		for (int i = 0; i < n; i++) {
			grades[i] = Integer.parseInt(st.nextToken());
			if (grades[i] > max) {
				max = grades[i];
			}
		}
		double sum = 0;
		for (int i = 0; i < n; i++) {
			grades[i] = ((double)grades[i] / max) * 100;
			sum += grades[i];
		}
		System.out.println(sum / n);


	}

}
