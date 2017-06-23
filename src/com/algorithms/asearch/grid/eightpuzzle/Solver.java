package com.algorithms.asearch.grid.eightpuzzle;
/**
 * --->3  8  0  
7  5  2  
6  4  1
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solver {

public static List<Block> aStarForNPuzzle(Block t){
	Block b=null;
	Queue<Block> queue=new PriorityQueue<Block>();
	queue.add(t);
	List<Block> path=new ArrayList<Block>();
	List<Block> visited=new ArrayList<Block>();
	if(!t.isSolvable()){
		return visited;
	}
	while(!queue.isEmpty()){
		b=queue.poll();
		visited.add(b);
		if(b.isGoal()){
			while(b!=null) {
				path.add(b);
				b=b.parent;
			}
			return path;
		}
		for(Block tmp:b.getNeighbours()){
			if(!visited.contains(tmp)){
				tmp.parent=b;
				queue.add(tmp);
			}
		}
	}
	return visited;	
}
public static void main(String[] args) {
	int n=3;
	Block b=new Block(getRandomArray(n),0);
    while(!b.isSolvable()) {
        b = new Block(getRandomArray(n), 0);
    }
    System.out.println("--->"+b);
	int[][] t1={{2,3,4},{8,1,7},{6,5,0}};
	//b=new Block(t1, 0);
	List<Block> path=aStarForNPuzzle(b);
	Collections.reverse(path);
	
	if(path.size()>0){
		for(Block p:path) System.out.println(p);
	}
	else System.out.println("Not Solvable");
	System.out.println("steps:"+path.size());
	
}
private static int[][] getRandomArray(Integer n) {
    int[][] ar=new int[n][n];
    List<Integer> list = new ArrayList<>();
    int k=0;
    for(int i=0;i<n*n;i++){
    	list.add(i);
    }
    Collections.shuffle(list);
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            ar[i][j]=list.get(k++);     
        }
    }
    return ar;
}
}
