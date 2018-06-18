package me.sylvaeon.synthesis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Heatmap<E> {

	private Map<E, Counter> map;

	public Heatmap(Collection<E> values) {
		map = new HashMap<>();
		for(E e : values) {
			map.put(e, new Counter());
		}
	}

	public Map<E, Counter> getMap() {
		return map;
	}

	public void setMap(Map<E, Counter> map) {
		this.map = map;
	}

	public void increment(E e) {
		map.get(e).increment();
	}

	public double getScalar(E e) {
		int largest = getLargest();
		int count = map.get(e).getCount();
		return (count == 0) ? 0 : (double) count / largest;
	}

	public int getLargest() {
		int largest = 0;
		for(Map.Entry<E, Counter> entry : map.entrySet()) {
			if(entry.getValue().getCount() > largest) {
				largest = entry.getValue().getCount();
			}
		}
		return largest;
	}

}
