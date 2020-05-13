package samsamg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class EXAM07_FindCriminal {
	static int[][] map;
	static boolean[][] checkMap;
	static BufferedReader br;
	static StringTokenizer st;
	static int ROW, COL, initR, initC, TIME;
	static int nextR, nextC;
	static int[] dirRow = { -1, 1, 0, 0 };
	static int[] dirCol = { 0, 0, -1, 1 };
	static Queue<Pos2> q;
	static int ans;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int TC = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TC; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			ROW = Integer.parseInt(st.nextToken());
			COL = Integer.parseInt(st.nextToken());
			initR = Integer.parseInt(st.nextToken());
			initC = Integer.parseInt(st.nextToken());
			TIME = Integer.parseInt(st.nextToken());
			
			map = new int[ROW][COL];
			
			
			for (int i = 0; i < ROW; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < COL; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			q = new LinkedList<Pos2>();
			ans =1;
			bfs(new Pos2(initR, initC, TIME-1));
			sb.append("#" + tc +" " + ans + "\n");
			
		} // end of TestCase
		System.out.println(sb.toString());
	} // end of main

	static void bfs(Pos2 pos) {
		q.offer(pos);
		checkMap = new boolean[ROW][COL];
		checkMap[pos.getI()][pos.getJ()] = true;

		while (!q.isEmpty()) {
			Pos2 temp = q.poll();
			
			int beforR = temp.getI();
			int beforC = temp.getJ();
			if (map[beforR][beforC] == 1) {
				for (int n = 0; n < 4; n++) {
					nextR = temp.getI() + dirRow[n];
					nextC = temp.getJ() + dirCol[n];
					if (nextR < 0 || nextC < 0 || nextR >= ROW || nextC >= COL || map[nextR][nextC] == 0) continue;
					if(n==0 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 4 || map[nextR][nextC] == 7)) continue;
					else if(n==1 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 5 || map[nextR][nextC] == 6)) continue;
					else if(n==2 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 6 || map[nextR][nextC] == 7)) continue;
					else if(n==3 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 4 || map[nextR][nextC] == 5)) continue;
					
					if(!checkMap[nextR][nextC] &&  temp.getDist() > 0) {
						checkMap[nextR][nextC] = true;
						q.offer(new Pos2(nextR, nextC, temp.getDist() -1));
						ans++;
						TIME--;
					}
				}
			}else if (map[beforR][beforC] == 2) {
				for (int n = 0; n < 2; n++) {
					nextR = temp.getI() + dirRow[n];
					nextC = temp.getJ() + dirCol[n];
					if (nextR < 0 || nextC < 0 || nextR >= ROW || nextC >= COL || map[nextR][nextC] == 0) continue;
					if(n==0 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 4 || map[nextR][nextC] == 7)) continue;
					else if(n==1 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 5 || map[nextR][nextC] == 6)) continue;
					else if(n==2 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 6 || map[nextR][nextC] == 7)) continue;
					else if(n==3 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 4 || map[nextR][nextC] == 5)) continue;
					
					if(!checkMap[nextR][nextC] &&  temp.getDist() > 0) {
						checkMap[nextR][nextC] = true;
						q.offer(new Pos2(nextR, nextC, temp.getDist() -1));
						ans++;
						TIME--;
					}
				}
			}else if (map[beforR][beforC] == 3) {
				for (int n = 2; n < 4; n++) {
					nextR = temp.getI() + dirRow[n];
					nextC = temp.getJ() + dirCol[n];
					if (nextR < 0 || nextC < 0 || nextR >= ROW || nextC >= COL || map[nextR][nextC] == 0) continue;
					if(n==0 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 4 || map[nextR][nextC] == 7)) continue;
					else if(n==1 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 5 || map[nextR][nextC] == 6)) continue;
					else if(n==2 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 6 || map[nextR][nextC] == 7)) continue;
					else if(n==3 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 4 || map[nextR][nextC] == 5)) continue;
					
					if(!checkMap[nextR][nextC] &&  temp.getDist() > 0) {
						checkMap[nextR][nextC] = true;
						q.offer(new Pos2(nextR, nextC, temp.getDist() -1));
						ans++;
						TIME--;
					}
				}
			}else if (map[beforR][beforC] == 4) {
				for (int n = 0; n < 4; n+=3) {
					nextR = temp.getI() + dirRow[n];
					nextC = temp.getJ() + dirCol[n];
					if (nextR < 0 || nextC < 0 || nextR >= ROW || nextC >= COL || map[nextR][nextC] == 0) continue;
					if(n==0 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 4 || map[nextR][nextC] == 7)) continue;
					else if(n==1 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 5 || map[nextR][nextC] == 6)) continue;
					else if(n==2 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 6 || map[nextR][nextC] == 7)) continue;
					else if(n==3 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 4 || map[nextR][nextC] == 5)) continue;
					
					if(!checkMap[nextR][nextC] &&  temp.getDist() > 0) {
						checkMap[nextR][nextC] = true;
						q.offer(new Pos2(nextR, nextC, temp.getDist() -1));
						ans++;
						TIME--;
					}
				}
			}else if (map[beforR][beforC] == 5) {
				for (int n = 1; n < 4; n+=2) {
					nextR = temp.getI() + dirRow[n];
					nextC = temp.getJ() + dirCol[n];
					if (nextR < 0 || nextC < 0 || nextR >= ROW || nextC >= COL || map[nextR][nextC] == 0) continue;
					if(n==0 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 4 || map[nextR][nextC] == 7)) continue;
					else if(n==1 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 5 || map[nextR][nextC] == 6)) continue;
					else if(n==2 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 6 || map[nextR][nextC] == 7)) continue;
					else if(n==3 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 4 || map[nextR][nextC] == 5)) continue;
					
					if(!checkMap[nextR][nextC] &&  temp.getDist() > 0) {
						checkMap[nextR][nextC] = true;
						q.offer(new Pos2(nextR, nextC, temp.getDist() -1));
						ans++;
						TIME--;
					}
				}
			}else if (map[beforR][beforC] == 6) {
				for (int n = 1; n < 3; n++) {
					nextR = temp.getI() + dirRow[n];
					nextC = temp.getJ() + dirCol[n];
					if (nextR < 0 || nextC < 0 || nextR >= ROW || nextC >= COL || map[nextR][nextC] == 0) continue;
					if(n==0 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 4 || map[nextR][nextC] == 7)) continue;
					else if(n==1 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 5 || map[nextR][nextC] == 6)) continue;
					else if(n==2 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 6 || map[nextR][nextC] == 7)) continue;
					else if(n==3 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 4 || map[nextR][nextC] == 5)) continue;
					
					if(!checkMap[nextR][nextC] &&  temp.getDist() > 0) {
						checkMap[nextR][nextC] = true;
						q.offer(new Pos2(nextR, nextC, temp.getDist() -1));
						ans++;
						TIME--;
					}
				}
			}else if (map[beforR][beforC] == 7) {
				for (int n = 0; n < 4; n+=2) {
					nextR = temp.getI() + dirRow[n];
					nextC = temp.getJ() + dirCol[n];
					if (nextR < 0 || nextC < 0 || nextR >= ROW || nextC >= COL || map[nextR][nextC] == 0) continue;
					if(n==0 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 4 || map[nextR][nextC] == 7)) continue;
					else if(n==1 && (map[nextR][nextC] == 3 || map[nextR][nextC] == 5 || map[nextR][nextC] == 6)) continue;
					else if(n==2 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 6 || map[nextR][nextC] == 7)) continue;
					else if(n==3 && (map[nextR][nextC] == 2 || map[nextR][nextC] == 4 || map[nextR][nextC] == 5)) continue;
					
					if(!checkMap[nextR][nextC] &&  temp.getDist() > 0) {
						checkMap[nextR][nextC] = true;
						q.offer(new Pos2(nextR, nextC, temp.getDist() -1));
						ans++;
						TIME--;
					}
				}
			}
			
		} //end of while
	} // end of bfs
	static class Pos2 {
		int i, j, dist;

		public Pos2(int i, int j, int dist) {
			super();
			this.i = i;
			this.j = j;
			this.dist = dist;
		}

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

		public int getDist() {
			return dist;
		}

		public void setDist(int dist) {
			this.dist = dist;
		}
	}
}
