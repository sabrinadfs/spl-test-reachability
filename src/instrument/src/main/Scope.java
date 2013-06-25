package main;

import java.util.Arrays;

public class Scope {
	
	int begin;
	int end;
	boolean active;
	String[] features;

	public Scope(int start, int end, boolean active, String[] features) {
		super();
		this.begin = start;
		this.end = end;
		this.active = active;
		this.features = features;
	}

	@Override
	public String toString() {
		return "Scope [begin=" + begin + ", end=" + end + ", active=" + active
				+ ", features=" + Arrays.toString(features) + "]";
	}
}
