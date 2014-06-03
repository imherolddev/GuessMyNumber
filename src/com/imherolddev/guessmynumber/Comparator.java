package com.imherolddev.guessmynumber;

public class Comparator {

	private String result = "";

	public String compare(int number, int guess) {

		if (guess != number) {

			if (guess > number)
				result = "high";
			else {
				result = "low";
			}

		} else {

			result = "correct";

		}

		return result;

	}

}
