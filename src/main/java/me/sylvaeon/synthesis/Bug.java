package me.sylvaeon.synthesis;

public class Bug {

	public static final int DIRECTION_NONE = -1, DIRECTION_LEFT = 0, DIRECTION_UP = 1, DIRECTION_RIGHT = 2, DIRECTION_DOWN = 3;
	public static final int MAX_X = 10, MAX_Y = 4;

	int direction;
	int x, y;

	public Bug() {
		direction = 0;
		x = 1;
		y = 2;
	}

	public void update() {
		switch (direction) {
			case DIRECTION_LEFT:
				x--;
				break;
			case DIRECTION_UP:
				y--;
				break;
			case DIRECTION_RIGHT:
				x++;
				break;
			case DIRECTION_DOWN:
				y++;
				break;
		}
		if(x >= MAX_X) {
			x = MAX_X - 1;
		}
		if(y >= MAX_Y) {
			y = MAX_Y - 1;
		}
		if(x < 0) {
			x = 0;
		}
		if(y < 0) {
			y = 0;
		}
		setDirection(DIRECTION_NONE);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
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
}
