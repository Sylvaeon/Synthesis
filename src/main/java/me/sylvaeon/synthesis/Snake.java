package me.sylvaeon.synthesis;

import me.sylvaeon.synthesis.effects.SnakeKeyboardEffect;

import java.util.ArrayDeque;

public class Snake {
	public static final int DIRECTION_NONE = -1, DIRECTION_LEFT = 0, DIRECTION_UP = 1, DIRECTION_RIGHT = 2, DIRECTION_DOWN = 3;
	public static final int MAX_X = 10, MAX_Y = 4;

	int direction;

	ArrayDeque<Vector2d> coordinates;

	long lastUpdate;

	public Snake() {
		lastUpdate = System.currentTimeMillis();
		direction = DIRECTION_RIGHT;
		coordinates = new ArrayDeque<>();
		coordinates.addFirst(new Vector2d(MAX_X / 2, MAX_Y / 2));
	}

	private static long UPDATE_TIME = 200L;

	public void update(boolean[][] foodLocations) throws InterruptedException {
		if(System.currentTimeMillis() >= lastUpdate + UPDATE_TIME) {
			lastUpdate = System.currentTimeMillis();
			Vector2d headCoords = getHead();
			int x = headCoords.getX(), y = headCoords.getY();
			switch (direction) {
				case DIRECTION_LEFT:
					if (inBounds(x - 1, y)) {
						addFirst(--x, y);
						onMove(foodLocations, x, y);
					}
					break;
				case DIRECTION_UP:
					if (inBounds(x, y - 1)) {
						addFirst(x, --y);
						onMove(foodLocations, x, y);
					}
					break;
				case DIRECTION_RIGHT:
					if (inBounds(x + 1, y)) {
						addFirst(++x, y);
						onMove(foodLocations, x, y);
					}
					break;
				case DIRECTION_DOWN:
					if (inBounds(x, y + 1)) {
						addFirst(x, ++y);
						onMove(foodLocations, x, y);
					}
					break;
			}
		}
	}

	private void onMove(boolean[][] foodLocations, int x, int y) throws InterruptedException {
		if(foodLocations[y][x]) {
			foodLocations[y][x] = false;
			SnakeKeyboardEffect.refreshFood(this, foodLocations);
		} else {
			coordinates.removeLast();
		}
	}

	private boolean inBounds(int x, int y) {
		if(x >= MAX_X || x < 0 || y >= MAX_Y || y < 0) {
			return false;
		} else {
			return true;
		}
	}

	public Vector2d getHead() {
		return coordinates.getFirst();
	}

	private void addFirst(int x, int y) throws InterruptedException {
		coordinates.addFirst(new Vector2d(x, y));
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public ArrayDeque<Vector2d> getCoordinates() {
		return coordinates;
	}

}
