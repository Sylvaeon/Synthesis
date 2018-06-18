package me.sylvaeon.synthesis.effects;

import me.sylvaeon.synthesis.Counter;
import me.sylvaeon.synthesis.Heatmap;
import me.sylvaeon.synthesis.Main;
import org.jglr.jchroma.effects.CustomKeyboardEffect;
import org.jglr.jchroma.utils.ColorRef;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.*;
import java.util.Map;

public class HeatmapKeyboardEffect extends CustomKeyboardEffect implements Reactable, Updatable {

    private Heatmap<Integer> heatmap;

    private ColorRef color1, color2;

    public HeatmapKeyboardEffect(ColorRef color) {
    	this.color1 = color;
    	this.color2 = ColorRef.NULL;
        init();
    }

	public HeatmapKeyboardEffect(ColorRef color1, ColorRef color2) {
		this.color1 = color1;
		this.color2 = color2;
		init();
	}

	private void init() {
		heatmap = new Heatmap<>(Main.keyToRzKey.values());
	}

    @Override
    public void onKeyPress(NativeKeyEvent nativeKeyEvent) {
    	int key = nativeKeyEvent.getRawCode();
    	if(Main.keyToRzKey.containsKey(key)) {
			int rz_key = Main.keyToRzKey.get(key);
			System.out.println("Converted keycode: " + rz_key);
			heatmap.increment(rz_key);
		}
    }

    @Override
    public void update() {
    	for(Map.Entry<Integer, Counter> entry : heatmap.getMap().entrySet()) {
			int key = entry.getKey();
			double scalar = heatmap.getScalar(key);
			this.setKeyColor(key, colorToColorRef(averageColors(colorRefToColor(color1), colorRefToColor(color2), scalar)));
		}
    }

    private static Color colorRefToColor(ColorRef colorRef) {
    	return new Color(colorRef.getRed(), colorRef.getGreen(), colorRef.getBlue());
	}

	private static ColorRef colorToColorRef(Color color) {
    	return new ColorRef(color.getRed(), color.getGreen(), color.getBlue());
	}

	private static Color averageColors(Color c1, Color c2, double weight) {
		return new Color(
			(int) ((weight * c1.getRed()) + ((1 - weight) * c2.getRed())),
			(int) ((weight * c1.getGreen()) + ((1 - weight) * c2.getGreen())),
			(int) ((weight * c1.getBlue()) + ((1 - weight) * c2.getBlue()))
		);
	}

}
