package com.algorithms.asearch.grid.eightpuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solver {

public static List<Block> aStarForNPuzzle(Block b){
	Queue<Block> queue=new PriorityQueue<Block>();
	queue.add(b);
	//List<Block> path=new ArrayList<Block>();
	List<Block> visited=new ArrayList<Block>();
	if(!b.isSolvable()){
		return visited;
	}
	while(!queue.isEmpty()){
		b=queue.poll();
		visited.add(b);
		if(b.isGoal()){
			return visited;
		}
		for(Block tmp:b.getNeighbours()){
			if(!visited.contains(tmp)){
				queue.add(tmp);
			}
		}
	}
	return visited;	
}
public static void main(String[] args) {
	int[][] t1={{1,2,3,4},{5,6,0,8},{9,10,7,11},{13,14,15,12}};
	Block b=new Block(t1, 0);
	List<Block> path=aStarForNPuzzle(b);
	if(path.size()>0){
		for(Block p:path) System.out.println(p);
	}
	else System.out.println("Not Solvable");
	
}
}
