package me.sylvaeon.synthesis.effects;

import me.sylvaeon.synthesis.Counter;
import org.jglr.jchroma.effects.CustomKeyboardEffect;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.HashMap;
import java.util.Map;

public class HeatmapKeyboardEffect extends CustomKeyboardEffect implements Reactable, Updatable {
    private Map<Integer, Counter> counters;

    public HeatmapKeyboardEffect() {
        counters = new HashMap<>();
    }

    @Override
    public void onKeyPress(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void update() {

    }

}
