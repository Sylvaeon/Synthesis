package me.sylvaeon.synthesis.effects;

import org.jnativehook.keyboard.NativeKeyEvent;

public interface Reactable {
	void onKeyPress(NativeKeyEvent nativeKeyEvent);
}
