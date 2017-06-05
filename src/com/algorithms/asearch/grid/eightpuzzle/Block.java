package com.algorithms.asearch.grid.eightpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * represents an N size Puzzle board with numbers ranging from 0 to n*n-1
 * 
 * @author Anwesh
 * 
 */
public class Block implements Comparable<Block> {
	int[][] ar;
	int numberOfMovesMade;

	public Block(int n) {
		ar = new int[n][n];
		numberOfMovesMade = 0;
	}

	public Block(int[][] a, int numMoves) {
		ar = a;
		numberOfMovesMade = numMoves;
	}

	public List<Block> getNeighbours() {
		int n = ar.length;
		List<Block> neighbours = new ArrayList<Block>();
		int index_i = 0, index_j = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (ar[i][j] == 0) {
					index_i = i;
					index_j = j;
					break;
				}
			}
		}
		if (index_j - 1 >= 0) {
			neighbours.add(getNeighBourBlock(index_i, index_j, 0, -1));
		}
		if (index_j + 1 < n) {
			neighbours.add(getNeighBourBlock(index_i, index_j, 0, 1));
		}
		if (index_i - 1 >= 0) {
			neighbours.add(getNeighBourBlock(index_i, index_j, -1, 0));
		}
		if (index_i + 1 < n) {
			neighbours.add(getNeighBourBlock(index_i, index_j, 1, 0));
		}
		return neighbours;
	}

	private Block getNeighBourBlock(int index_i, int index_j, int i, int j) {
		int[][] tmp = getBlockArrayCopy();
		tmp[index_i][index_j] = tmp[index_i + i][index_j + j];
		tmp[index_i + i][index_j + j] = 0;
		return new Block(tmp, numberOfMovesMade + 1);
	}

	public boolean isSolvable() {
		int n = ar.length;
		int inv = getInversionCount();
		if (n % 2 == 0) {
			return (getBlankRow() + inv) % 2 == 1;
		} else {
			return inv % 2 == 0;
		}
	}

	private int getBlankRow() {
		int n = ar.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (ar[i][j] == 0)
					return i;
			}
		}
		return 0;
	}

	private int getInversionCount() {
		int inv = 0;
		int k = 0;
		int n = ar.length;
		int[] tmp = new int[n * n - 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (ar[i][j] != 0) {
					tmp[k] = ar[i][j];
					k++;
				}
			}
		}
		for (int i = 0; i < n * n - 1; i++) {
			for (int j = i + 1; j < n * n - 1; j++) {
				if (tmp[i] > tmp[j]) {
					inv++;
				}
			}
		}

		return inv;
	}

	public int getHammingPriority() {
		int num = 1;
		int p = 0;
		int n = ar.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (!(i == n - 1 && j == n - 1)) {
					if (ar[i][j] != num) {
						p++;
					}
					num++;
				}
			}
		}
		return p + numberOfMovesMade;
	}

	public int getSize() {
		return ar.length;
	}

	@Override
	public String toString() {
		int n = ar.length;
		String s = "";
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s = s + ar[i][j] + "  ";
			}
			s = s + "\n";
		}
		return s;
	}

	public boolean isGoal() {
		if (getHammingPriority() - numberOfMovesMade == 0) {
			return true;
		}
		return false;
	}

	public int[][] getBlockArrayCopy() {
		int n = ar.length;
		int[][] tmp = new int[n][n];
		for (int i = 0; i < n; i++) {
			tmp[i] = Arrays.copyOf(ar[i], n);
		}
		return tmp;
	}

	@Override
	public int compareTo(Block b) {
		return getHammingPriority() - b.getHammingPriority();
	}

	private boolean equlas(Block b) {
		return ar == b.ar;
	}
}
