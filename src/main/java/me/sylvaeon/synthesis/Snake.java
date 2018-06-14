package me.sylvaeon.synthesis;

import java.util.concurrent.BlockingDeque;

public class Snake {
	public static final int DIRECTION_NONE = -1, DIRECTION_LEFT = 0, DIRECTION_UP = 1, DIRECTION_RIGHT = 2, DIRECTION_DOWN = 3;
	public static final int MAX_X = 10, MAX_Y = 4;

	int direction;

	BlockingDeque<Vector2d> coordinates;

	public Snake() {

	}

}
