package me.sylvaeon.synthesis.effects;

import me.sylvaeon.synthesis.Bug;
import org.jglr.jchroma.effects.CustomKeyboardEffect;
import org.jglr.jchroma.utils.ColorRef;
import org.jglr.jchroma.utils.KeyboardKeys;
import org.jnativehook.keyboard.NativeKeyEvent;

public class BugKeyboardEffect extends CustomKeyboardEffect implements Updatable, Reactable {

	private static int[][] BUG_KEYS;
	private static Bug bug;

	ColorRef primaryColor;
	ColorRef secondaryColor;
	ColorRef backgroundColor1;
	ColorRef backgroundColor2;

	public BugKeyboardEffect(ColorRef primaryColor, ColorRef secondaryColor, ColorRef backgroundColor1, ColorRef backgroundColor2) {
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.backgroundColor1 = backgroundColor1;
		this.backgroundColor2 = backgroundColor2;
		BUG_KEYS = new int[4][14];
		for(int i = 0; i < Bug.MAX_Y; i++) {
			for(int j = 0; j < Bug.MAX_X; j++) {
				BUG_KEYS[i][j] = (0x100 * (i + 1)) + j + 2;
			}
		}
		for(int i = 0; i < BUG_KEYS[3].length; i++) {
			BUG_KEYS[3][i]++;
		}
		bug = new Bug();

		this.setKeyColor(KeyboardKeys.RZKEY_LEFT, secondaryColor);
		this.setKeyColor(KeyboardKeys.RZKEY_UP, secondaryColor);
		this.setKeyColor(KeyboardKeys.RZKEY_RIGHT, secondaryColor);
		this.setKeyColor(KeyboardKeys.RZKEY_DOWN, secondaryColor);
	}

	public void update() {
		bug.update();
		ColorRef color;
		for (int x = 0; x < Bug.MAX_X; x++) {
			for (int y = 0; y < Bug.MAX_Y; y++) {
				color = (x % 2 == 0) ? backgroundColor1 : backgroundColor2;
				this.setKeyColor(BUG_KEYS[y][x], color);
			}
		}
		this.setKeyColor(BUG_KEYS[bug.getY()][bug.getX()], primaryColor);
	}

	public void onKeyPress(NativeKeyEvent nativeKeyEvent) {
		switch (nativeKeyEvent.getRawCode()) {
			case 0x25:
				bug.setDirection(0);
				break;
			case 0x26:
				bug.setDirection(1);
				break;
			case 0x27:
				bug.setDirection(2);
				break;
			case 0x28:
				bug.setDirection(3);
				break;
		}
	}

}
