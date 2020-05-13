package samsamg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class EXAM04_FindMine {
	static BufferedReader br;
	static int TC, IN, checkX, checkY, count, nextX, nextY;
	static StringTokenizer st;
	static char[][] map;
	static int[] dirX = { 1, -1, 0, 0, -1, -1, 1, 1 };
	static int[] dirY = { 0, 0, 1, -1, -1, 1, 1, -1 };
	static Queue<Pos> q;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		TC = Integer.parseInt(br.readLine());

		for (int tc = 0; tc < TC; tc++) {
			IN = Integer.parseInt(br.readLine());
			map = new char[IN][IN];
			for (int i = 0; i < IN; i++) {
				String str = br.readLine();
				for (int j = 0; j < IN; j++) {
					map[i][j] = str.charAt(j);
				}
			}

			q = new LinkedList<Pos>();
			count = 0;
			
			for (int i = 0; i < IN; i++) {
				for (int j = 0; j < IN; j++) {
					if (map[i][j] == '.' && check(i, j)) {
						bfs(new Pos(i, j));
						count++;
					}
				}
			}
			for (int i = 0; i < IN; i++) {
				for (int j = 0; j < IN; j++) {
					if (map[i][j] == '.') {
						count++;
					}
				}
			}
			System.out.println("#" + TC + " " + count);
		}

	}

	public static void bfs(Pos pos) {
		q.offer(pos);
		map[pos.getX()][pos.getY()] = '#';
		
		while (!q.isEmpty()) {
			Pos temp = q.poll();
			for (int check = 0; check < 8; check++) {
				nextX = temp.getX() + dirX[check];
				nextY = temp.getY() + dirY[check];
				if (nextX >= 0 && nextY >= 0 && nextX < IN && nextY < IN && map[nextX][nextY] == '.') {
					if (check(nextX, nextY)) 
						q.offer(new Pos(nextX, nextY));
					map[nextX][nextY] = '#';
				}
			}
		}
	}

	static boolean check(int i, int j) {
		for (int check = 0; check < 8; check++) {
			checkX = i + dirX[check];
			checkY = j + dirY[check];
			if (checkX >= 0 && checkY >= 0 && checkX < IN && checkY < IN) {
				if (map[checkX][checkY] == '*') 
					return false;
			}
		}
		return true;
	}

}

class Pos {
	private int x, y;

	public Pos(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}