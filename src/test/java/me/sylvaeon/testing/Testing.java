package me.sylvaeon.testing;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Testing {
	public static void main(String[] args) {
		Deque<String> deque = new ArrayDeque<>();
		deque.add("Apple");
		deque.add("Banana");
		deque.add("Cranberry");
		int i = 0;
		for(Iterator<String> iterator = deque.iterator(); iterator.hasNext(); i++) {
			System.out.println(i + ": " + iterator.next());
		}
		i = 0;
		for(Iterator<String> iterator = deque.iterator(); iterator.hasNext(); i++) {
			String next = iterator.next();
			System.out.println(i + ": " + next);
		}
	}
}
