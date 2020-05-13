package samsamg;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Test2 {

	static int size, sx, sy, max;
	static int[][] map = new int[20][20];
	static boolean[] pick = new boolean[101];
	static int[] dx = { 1, 1, -1, -1 };
	static int[] dy = { -1, 1, 1, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int tnum = stoi(br.readLine());

		for (int t = 1; t <= tnum; t++) {
			size = stoi(br.readLine());
			max = -1;
			for (int i = 0; i < size; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < size; j++) {
					map[i][j] = stoi(st.nextToken());
				}
			}

			for (int i = 0; i < size - 1; i++) {
				for (int j = 1; j < size - 1; j++) {
					sx = i;
					sy = j;
					pick[map[i][j]] = true;
					backTracking(i, j, 0);
					pick[map[i][j]] = false;
				}
			}

			System.out.println("#" + t + " " + max);
		}
	}

	private static void backTracking(int x, int y, int d) {
		for (int i = d; i < d + 2 && i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];

			if (nx >= 0 && ny >= 0 && nx < size && ny < size) {
				if (nx == sx && ny == sy) {
					calc();
					return;
				}
				if (!pick[map[nx][ny]]) {
					pick[map[nx][ny]] = true;
					backTracking(nx, ny, i);
					pick[map[nx][ny]] = false;
				}
			}
		}
	}

	private static void calc() {
		int count = 0;
		for (int i = 1; i <= 100; i++) {
			if (pick[i])
				count++;
		}
		max = Math.max(max, count);
	}

	private static int stoi(String input) {
		return Integer.parseInt(input);
	}
}