package me.sylvaeon.synthesis.effects;

import me.sylvaeon.synthesis.Snake;
import me.sylvaeon.synthesis.Vector2d;
import org.jglr.jchroma.effects.CustomKeyboardEffect;
import org.jglr.jchroma.utils.ColorRef;
import org.jglr.jchroma.utils.KeyboardKeys;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import static me.sylvaeon.synthesis.Snake.MAX_X;
import static me.sylvaeon.synthesis.Snake.MAX_Y;

public class SnakeKeyboardEffect extends CustomKeyboardEffect implements Updatable, Reactable {

	private static boolean[][] foodLocations;
	private static int[][] SNAKE_KEYS;
	private static Snake snake;

	ColorRef primaryColor;
	ColorRef secondaryColor;
	ColorRef backgroundColor1;
	ColorRef backgroundColor2;

	public SnakeKeyboardEffect(ColorRef primaryColor, ColorRef secondaryColor, ColorRef backgroundColor1, ColorRef backgroundColor2) {
		snake = new Snake();
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.backgroundColor1 = backgroundColor1;
		this.backgroundColor2 = backgroundColor2;
		SNAKE_KEYS = new int[4][14];
		foodLocations = new boolean[4][14];
		for(int i = 0; i < MAX_Y; i++) {
			for(int j = 0; j < MAX_X; j++) {
				SNAKE_KEYS[i][j] = (0x100 * (i + 1)) + j + 2;
				foodLocations[i][j] = false;
			}
		}
		refreshFood(snake, foodLocations);
		for(int i = 0; i < SNAKE_KEYS[3].length; i++) {
			SNAKE_KEYS[3][i]++;
		}
		snake = new Snake();

		this.setKeyColor(KeyboardKeys.RZKEY_LEFT, secondaryColor);
		this.setKeyColor(KeyboardKeys.RZKEY_UP, secondaryColor);
		this.setKeyColor(KeyboardKeys.RZKEY_RIGHT, secondaryColor);
		this.setKeyColor(KeyboardKeys.RZKEY_DOWN, secondaryColor);
	}

	public void update() {
		try {
			snake.update(foodLocations);
			if(dequeContainsHeadInBody(snake.getCoordinates())) {
				onDeath();
			} else {
				ColorRef color;
				for (int x = 0; x < MAX_X; x++) {
					for (int y = 0; y < MAX_Y; y++) {
						if (foodLocations[y][x]) {
							this.setKeyColor(SNAKE_KEYS[y][x], secondaryColor);
						} else {
							color = (x % 2 == 0) ? backgroundColor1 : backgroundColor2;
							this.setKeyColor(SNAKE_KEYS[y][x], color);
						}
					}
				}
				for (Vector2d vector : snake.getCoordinates()) {
					this.setKeyColor(SNAKE_KEYS[vector.getY()][vector.getX()], primaryColor);
				}
				this.setKeyColor(KeyboardKeys.RZKEY_LEFT, snake.getDirection() == Snake.DIRECTION_LEFT ? primaryColor : secondaryColor);
				this.setKeyColor(KeyboardKeys.RZKEY_UP, snake.getDirection() == Snake.DIRECTION_UP ? primaryColor : secondaryColor);
				this.setKeyColor(KeyboardKeys.RZKEY_RIGHT, snake.getDirection() == Snake.DIRECTION_RIGHT ? primaryColor : secondaryColor);
				this.setKeyColor(KeyboardKeys.RZKEY_DOWN, snake.getDirection() == Snake.DIRECTION_DOWN ? primaryColor : secondaryColor);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void onDeath() {
		Object lock = new Object();
		try {
			snake = new Snake();
			for(int i = 0; i < MAX_Y; i++) {
				for(int j = 0; j < MAX_X; j++) {
					foodLocations[i][j] = false;
				}
			}
			refreshFood(snake, foodLocations);
			ColorRef color;
			for(int i = 0; i < 10; i++) {
				for (int x = 0; x < MAX_X; x++) {
					for (int y = 0; y < MAX_Y; y++) {
						color = ((x + i) % 2 == 0) ? backgroundColor1 : backgroundColor2;
						this.setKeyColor(SNAKE_KEYS[y][x], color);
					}
				}
				synchronized (lock) {
					lock.wait(250);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onKeyPress(NativeKeyEvent nativeKeyEvent) {
		switch (nativeKeyEvent.getRawCode()) {
			case 0x25:
				if(snake.getDirection() != Snake.DIRECTION_RIGHT) snake.setDirection(Snake.DIRECTION_LEFT);
				break;
			case 0x26:
				if(snake.getDirection() != Snake.DIRECTION_DOWN) snake.setDirection(Snake.DIRECTION_UP);
				break;
			case 0x27:
				if(snake.getDirection() != Snake.DIRECTION_LEFT) snake.setDirection(Snake.DIRECTION_RIGHT);
				break;
			case 0x28:
				if(snake.getDirection() != Snake.DIRECTION_UP) snake.setDirection(Snake.DIRECTION_DOWN);
				break;
		}
	}

	public static void refreshFood(Snake snake, boolean[][] foodLocations) {
		ThreadLocalRandom current = ThreadLocalRandom.current();
		int randX, randY;
		do {
			randX = current.nextInt(MAX_X);
			randY = current.nextInt(MAX_Y);
		} while (containsVector(snake.getCoordinates(), new Vector2d(randX, randY)));
		foodLocations[randY][randX] = true;
	}

	private static boolean containsVector(Deque<Vector2d> deque, Vector2d vector) {
		for (Iterator<Vector2d> iterator = deque.iterator(); iterator.hasNext();) {
			if(iterator.next().equals(vector)) {
				return true;
			}
		}
		return false;
	}

	private static boolean dequeContainsHeadInBody(ArrayDeque deque) {
		ArrayDeque clone = deque.clone();
		clone.removeFirst();
		for(Iterator iterator = clone.iterator(); iterator.hasNext();) {
			if(iterator.next().equals(deque.peekFirst())) {
				return true;
			}
		}
		return false;
	}

}
