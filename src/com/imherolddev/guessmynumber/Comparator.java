package com.imherolddev.guessmynumber;

import android.content.Context;

public class Comparator {
	
	Context c;
	
	public Comparator(Context c) {
		this.c = c;
	}

	private String result = "";

	public String compare(int number, int guess) {

		if (guess != number) {

			if (guess > number)
				result = c.getString(R.string.high);
			else {
				result = c.getString(R.string.low);
			}

		} else {

			result = c.getString(R.string.correct);

		}

		return result;

	}

}
