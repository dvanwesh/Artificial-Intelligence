package com.algorithms.asearch.grid.shortdistance;

public class Cell implements Comparable<Cell>{
int x;
int y;
int heuristicCost=0;
int finalCost=0; //f()=g()+h()
Cell parent;
boolean isDiagonal;
public Cell(int x,int y){
	this.x=x;
	this.y=y;
}
public int compareTo(Cell O) {
	return this.finalCost-O.finalCost;
}
@Override
	public boolean equals(Object O) {
		Cell c=(Cell)O;
		return this.x==c.x&&this.y==c.y;
	}
@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+this.x+","+this.y+"]";
	}
}
