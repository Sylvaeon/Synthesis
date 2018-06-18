package me.sylvaeon.synthesis;

public class Counter implements Comparable<Counter> {
    private int count;

    public Counter() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public void reset() {
        count = 0;
    }

	@Override
	public int compareTo(Counter o) {
		return new Integer(count).compareTo(o.count);
	}
}
