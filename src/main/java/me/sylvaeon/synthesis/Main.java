package me.sylvaeon.synthesis;

import me.sylvaeon.synthesis.effects.Reactable;
import me.sylvaeon.synthesis.effects.SnakeKeyboardEffect;
import me.sylvaeon.synthesis.effects.Updatable;
import org.jglr.jchroma.JChroma;
import org.jglr.jchroma.effects.KeyboardEffect;
import org.jglr.jchroma.utils.ColorRef;
import org.jglr.jchroma.utils.KeyboardKeys;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main implements NativeKeyListener {

	public static Map<Integer, Integer> keyToRzKey;

	private static KeyboardEffect activeKeyboardEffect;
	private static JChroma chroma;

	public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

	}

	public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
		if (activeKeyboardEffect instanceof Reactable) {
			((Reactable) activeKeyboardEffect).onKeyPress(nativeKeyEvent);
		}
	}

	public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

	}

	public static void main(String[] args) {
		keyToRzKey = new HashMap<>();
		keyToRzKey.put(81, KeyboardKeys.RZKEY_Q);
		keyToRzKey.put(87, KeyboardKeys.RZKEY_W);
		keyToRzKey.put(69, KeyboardKeys.RZKEY_E);
		keyToRzKey.put(82, KeyboardKeys.RZKEY_R);
		keyToRzKey.put(84, KeyboardKeys.RZKEY_T);
		keyToRzKey.put(89, KeyboardKeys.RZKEY_Y);
		keyToRzKey.put(85, KeyboardKeys.RZKEY_U);
		keyToRzKey.put(73, KeyboardKeys.RZKEY_I);
		keyToRzKey.put(79, KeyboardKeys.RZKEY_O);
		keyToRzKey.put(80, KeyboardKeys.RZKEY_P);
		keyToRzKey.put(65, KeyboardKeys.RZKEY_A);
		keyToRzKey.put(83, KeyboardKeys.RZKEY_S);
		keyToRzKey.put(68, KeyboardKeys.RZKEY_D);
		keyToRzKey.put(70, KeyboardKeys.RZKEY_F);
		keyToRzKey.put(71, KeyboardKeys.RZKEY_G);
		keyToRzKey.put(72, KeyboardKeys.RZKEY_H);
		keyToRzKey.put(74, KeyboardKeys.RZKEY_J);
		keyToRzKey.put(75, KeyboardKeys.RZKEY_K);
		keyToRzKey.put(76, KeyboardKeys.RZKEY_L);
		keyToRzKey.put(90, KeyboardKeys.RZKEY_Z);
		keyToRzKey.put(88, KeyboardKeys.RZKEY_X);
		keyToRzKey.put(67, KeyboardKeys.RZKEY_C);
		keyToRzKey.put(86, KeyboardKeys.RZKEY_V);
		keyToRzKey.put(66, KeyboardKeys.RZKEY_B);
		keyToRzKey.put(78, KeyboardKeys.RZKEY_N);
		keyToRzKey.put(77, KeyboardKeys.RZKEY_M);

		chroma = JChroma.getInstance();
		chroma.init();
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		GlobalScreen.addNativeKeyListener(new Main());

		setKeyboardEffect(new SnakeKeyboardEffect(new ColorRef(0xFF, 0, 0), new ColorRef(0, 0xFF, 0), new ColorRef(0x01, 0x01, 0x01), new ColorRef(0x11, 0x11, 0x11)));
//		setKeyboardEffect(new HeatmapKeyboardEffect(new ColorRef(0xFF, 0x00, 0x00), new ColorRef(0x11, 0x11, 0x11)));

		long sleepTime = 10;
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					if(activeKeyboardEffect instanceof Updatable) {
						((Updatable) activeKeyboardEffect).update();
					}
					synchronized (this) {
						try {
							wait(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		thread.start();
		while(true) {
			chroma.createKeyboardEffect(activeKeyboardEffect);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setKeyboardEffect(KeyboardEffect effect) {
		activeKeyboardEffect = effect;
	}

}
