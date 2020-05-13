package samsamg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class EXAM08_FindTasteDesert {
	static BufferedReader br;
	static StringTokenizer st;
	static int[][] map;
	static boolean[][] checkMap;
	static int N, ROW, COL, initR, initC;
	static int nextR, nextC;
	static int[] dirRow = { -1, -1, 1, 1 };
	static int[] dirCol = { -1, 1, -1, 1 };
	static int MAX, ANS;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			N = Integer.parseInt(br.readLine());

			map = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			MAX = 0;
			ANS = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					checkMap = new boolean[N][N];
					checkMap[i][j] = true;
					initR = i;
					initC = j;
					dfs(i, j, 0);
					checkMap[i][j] = false;
				}
			}

			sb.append("#" + tc + " " + ANS + "\n");
		} // end of for tc
		System.out.println(sb.toString());
	} // end of main

	static void dfs(int i, int j, int d) {
		for (int r = d; r < d + 2 && r < 4; r++) {
			int nextR = i + dirRow[r];
			int nextC = j + dirCol[r];

			if (nextR < 0 || nextC < 0 || nextR >= N || nextC >= N)
				continue;
			if (initR == nextR && initC == nextC) {
				int cnt = 0;
				for (int n = 0; n < N; n++) {
					for (int m = 0; m < N; m++) {
						if (checkMap[n][m]) {
							cnt++;
						}
					}
				}
				ANS = cnt > MAX ? cnt : MAX;
				return;
			}
			if (!checkMap[nextR][nextC]) {
				checkMap[nextR][nextC] = true;
				dfs(nextR, nextC, r);
				checkMap[nextR][nextC] = false;
			}

		}

	}


}
