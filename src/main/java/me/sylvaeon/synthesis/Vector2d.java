package me.sylvaeon.synthesis;

public class Vector2d {
	int x, y;

	public Vector2d(int x, int y) {
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

	public void incrementX() {
		x++;
	}

	public void decrementX() {
		x--;
	}

	public void incrementY() {
		y++;
	}

	public void decrementY() {
		y--;
	}

}
