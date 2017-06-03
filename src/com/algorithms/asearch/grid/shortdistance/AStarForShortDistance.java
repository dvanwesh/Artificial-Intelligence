package com.algorithms.asearch.grid.shortdistance;

import java.util.PriorityQueue;
import java.util.Queue;

public class AStarForShortDistance {
	public final static int DIAGONAL_COST=14;
	public final static int ST_COST=10;
	static Queue<Cell> open;
	static boolean[][] closed;
	static boolean[][] path;
	static boolean[][] diagonalPath;
	static Cell[][] grid;
	static int start_i,start_j,end_i,end_j;
	public static void setBlockedCells(int i,int j){
		grid[i][j]=null;
	}
	public static void setSource(int i,int j){
		start_i=i;
		start_j=j;
	}
	public static void setDestination(int i,int j){
		end_i=i;
		end_j=j;
	}
	public static void checkAndUpdateCost(Cell current,Cell t,int cost,boolean isDiagonal){
		if(t==null || closed[t.x][t.y]) return;
		int currentFinaltotal=t.heuristicCost+cost;
		boolean inOpen=open.contains(t);
		if(!inOpen||currentFinaltotal<t.finalCost){
			t.finalCost=currentFinaltotal;
			t.isDiagonal=isDiagonal;
			t.parent=current;
			if(!inOpen){
				open.add(t);
			}
		}
	}
	public static void AStar(){
		open=new PriorityQueue<Cell>();
		open.add(grid[start_i][start_j]);
		Cell current;
		while(true){
			current=open.poll();
			if(current==null) break;
			closed[current.x][current.y]=true;
			if(current.equals(grid[end_i][end_j])) return;
			Cell neighbour;
			if(current.x-1>=0){
				neighbour=grid[current.x-1][current.y];
				checkAndUpdateCost(current, neighbour, current.finalCost+ST_COST,false);
				if(current.y-1>=0){
					neighbour=grid[current.x-1][current.y-1];
					checkAndUpdateCost(current, neighbour, current.finalCost+DIAGONAL_COST,true);
				}
				if(current.y+1<grid[0].length){
					neighbour=grid[current.x-1][current.y+1];
					checkAndUpdateCost(current, neighbour, current.finalCost+DIAGONAL_COST,true);
				}
			}
			if(current.y-1>=0){
				neighbour=grid[current.x][current.y-1];
				checkAndUpdateCost(current, neighbour, current.finalCost+ST_COST,false);
				if(current.x+1<grid.length){
					neighbour=grid[current.x+1][current.y-1];
					checkAndUpdateCost(current, neighbour, current.finalCost+DIAGONAL_COST,true);
				}
			}
			if(current.x+1<grid.length){
				neighbour=grid[current.x+1][current.y];
				checkAndUpdateCost(current, neighbour, current.finalCost+ST_COST,false);
				if(current.y+1<grid[0].length){
					neighbour=grid[current.x+1][current.y+1];
					checkAndUpdateCost(current, neighbour, current.finalCost+DIAGONAL_COST,true);
				}
			}
			if(current.y+1<grid[0].length){
				neighbour=grid[current.x][current.y+1];
				checkAndUpdateCost(current, neighbour, current.finalCost+ST_COST,false);
			}
		}
	}
	public static void test(int testCase,int x,int y,int s_x,int s_y,int e_x,int e_y,int[][] blocked){
		System.out.println("----Test case "+testCase);
		grid=new Cell[x][y];
		closed=new boolean[x][y];
		path=new boolean[x][y];
		diagonalPath=new boolean[x][y];
		setSource(s_x, s_y);
		setDestination(e_x, e_y);
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				grid[i][j]=new Cell(i,j);
				grid[i][j].heuristicCost=Math.abs(i-e_x)+Math.abs(j-e_y);
			}
		}
		grid[s_x][s_y].finalCost=0;
		for(int i=0;i<blocked.length;i++){
			setBlockedCells(blocked[i][0], blocked[i][1]);
		}
		printGrid(x,y,s_x,s_y,e_x,e_y);
		AStar();
		printGridWithCost(x,y);
		printPath(e_x,e_y);
		printGrid(x,y,s_x,s_y,e_x,e_y);
	}
	
	private static void printGridWithCost(int x, int y) {
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				if(grid[i][j]==null) System.out.print("B ");
				else System.out.print(grid[i][j].finalCost+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	private static void printGrid(int x,int y,int s_x,int s_y,int e_x,int e_y) {
		System.out.println("GRID---");
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				if(path[i][j] && !(i==s_x && j==s_y)){
					if(diagonalPath[i][j]){
						System.out.print("#   ");
					}
					else{
						System.out.print("#   ");
					}
				}
				else if(i==s_x && j==s_y){
					System.out.print("SO  ");
				}
				else if(i==e_x&&j==e_y){
					System.out.print("DE  ");
				}
				else if(grid[i][j]==null){
					System.out.print("BL  ");
				}
				else System.out.printf("%-3d ",0);
			}
			System.out.println();
		}
		System.out.println();
	}
	private static void printPath(int e_x,int e_y) {
		if(closed[e_x][e_y]){
			System.out.println("Path");
			Cell c=grid[e_x][e_y];
			while(c.parent!=null){
				System.out.print("->"+c.parent);
				path[c.parent.x][c.parent.y]=true;
				if(c.parent.isDiagonal) diagonalPath[c.parent.x][c.parent.y]=true;
				c=c.parent;
			}System.out.println();
		}
		else{
			System.out.println("NO possible path");
		}
	}
	public static void main(String[] args) throws Exception{   
        test(1, 5, 5, 0, 0, 3, 2, new int[][]{{0,4},{2,2},{3,1},{3,3}}); 
        test(2, 5, 5, 0, 0, 4, 4, new int[][]{{0,4},{2,2},{3,1},{3,3}});   
        test(3, 7, 7, 2, 1, 5, 4, new int[][]{{4,1},{4,3},{5,3},{2,3}});
        
        test(1, 5, 5, 0, 0, 4, 4, new int[][]{{3,4},{3,3},{4,3}});
    }
}
