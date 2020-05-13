package samsamg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class EXAM06_FindMountainWay {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	static int[][] map;
	static boolean[][] visited;
	static int max;
	static int result;
	static int nextI, nextJ;
	static int N, K;
	static int[] dirI = {-1, 1, 0, 0};
	static int[] dirJ = {0, 0, -1, 1};
	static ArrayList<Pos2> pos;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int TC = Integer.parseInt(br.readLine());
		for(int tc =1; tc <=TC; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			
			for(int i =0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int j = 0;j < N;j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if(map[i][j] > max) {
						max = map[i][j];
						
					}
				}
			}
			
			result = 0;
			visited = new boolean[N][N];
			pos = new ArrayList<Pos2>();
		
			for(int i =0; i < N; i++) {
				for(int j = 0;j < N;j++) {
						if(map[i][j] == max) {
							pos.add(new Pos2(i, j));
					}
				}
			}
			
			for(int i = 0; i < pos.size(); i++) {
				dfs(pos.get(i).getI(), pos.get(i).getJ(), 1, 1, max);
			}
			
			sb.append("#" + tc +" " + result + "\n");
			
		}
		System.out.println(sb.toString());
	}
	
	
	static void dfs(int i, int j, int count, int depth, int val) {
		if(result < depth) {
			result = depth;
		}
		
		visited[i][j] = true;
		for(int p = 0; p<4; p++) {
				nextI = i + dirI[p];
				nextJ = j + dirJ[p];
				if(nextI <0 || nextJ < 0 || nextI >= N || nextJ >= N) {
					continue;
				}
				if(visited[nextI][nextJ] == true) {
					continue;
				}
				
				if(count == 1) {
					if(val > map[nextI][nextJ]) {
						dfs(nextI, nextJ, 1, depth +1,map[nextI][nextJ]);
					}else if(val > map[nextI][nextJ] - K){
						 dfs(nextI, nextJ, 0, depth +1, val-1);
					}
				}else if(count == 0 && val > map[nextI][nextJ]){
					dfs(nextI, nextJ, 0, depth +1,map[nextI][nextJ]);
				}
				
		}
		visited[i][j] = false;
			
		
	}
}
class Pos2{
	private int i, j;

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public Pos2(int i, int j) {
		super();
		this.i = i;
		this.j = j;
	}
}
