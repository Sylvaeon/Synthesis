package me.sylvaeon.synthesis;

import me.sylvaeon.synthesis.effects.BugKeyboardEffect;
import me.sylvaeon.synthesis.effects.Reactable;
import me.sylvaeon.synthesis.effects.Updatable;
import org.jglr.jchroma.JChroma;
import org.jglr.jchroma.effects.KeyboardEffect;
import org.jglr.jchroma.utils.ColorRef;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main implements NativeKeyListener {

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



		setKeyboardEffect(new BugKeyboardEffect(new ColorRef(0xFF, 0, 0xFF), new ColorRef(0, 0xFF, 0xFF), new ColorRef(0x01, 0x01, 0x01), new ColorRef(0x11, 0x11, 0x11)));


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
