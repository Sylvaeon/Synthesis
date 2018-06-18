package me.sylvaeon.synthesis;

public class Vector2d {
	private int x, y;

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

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Vector2d) {
			Vector2d vector2d = (Vector2d) obj;
			if(vector2d.getX() == this.getX() && vector2d.getY() == this.getY()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "x:" + x + ",y:" + y;
	}
}
