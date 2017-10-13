package com.lwt.qt.chess;

public class Vertex {
	private int row;
	private int col;
	
	private char value;
	
	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public Vertex(int row, int col, char value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}

	@Override
	public String toString() {
		return "" + row + col;
	}
	
	
}
