package com.labrats.android.flip9.data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

/*
    The Cheat Class is to find the shortest solution to
    all puzzles in flip9
 */
public class Cheat {
	//Initialize variables
	private static final int[] masks = { 11, 23, 38, 89, 186, 308, 200, 464,
			416 };

	private static boolean[] visited = new boolean[512];
	private static int[] parent = new int[512], button = new int[512];
	private static ArrayList<Integer> ans = new ArrayList<Integer>();


	/*
	  c = the state of the button represented in binary [0-512)
      returns the solution in decreasing order.
	 */
	public static ArrayList<Integer> getCheat(int c) {
        if(c < 0 || c >= 512){
            return null;
        }
		Arrays.fill(visited, false);
		Arrays.fill(parent, -1);
		Arrays.fill(button, -1);
		ans.clear();
		bfs(c);
		int t = 0;
		while (t != -1) {
			ans.add(button[t]);
			t = parent[t];
		}

		ans.remove(ans.size() - 1);

		return ans;
	}

	//Breadth-first search to find best soltuion
	private static void bfs(int c) {
		ArrayDeque<Integer> q = new ArrayDeque<Integer>(10);
		visited[c] = true;
		q.add(c);
		while (!q.isEmpty()) {
			int t = q.poll();
			if (t == 0) {
				return;
			}
			int n = 0;
			for (int i : masks) {
				if (!visited[t ^ i]) {
					visited[t ^ i] = true;
					parent[t ^ i] = t;
					button[t ^ i] = n;
					q.add(t ^ i);
				}
				n++;
			}
		}
	}
}