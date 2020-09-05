package com.example.demo;

import java.util.regex.Pattern;

public class CheckValidParameters {

	public static final int MIN_RATING = 1;
	public static final int MAX_RATING = 5;

	public static boolean checkReview(Review review) {
		return checkEmail(review.getCustomer().getEmail()) && checkRating(review.getRating());
	}

	public static boolean checkEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null) {
			return false;
		}
		return pat.matcher(email).matches();

	}

	public static boolean checkRating(int rating) {
		return rating >= MIN_RATING && rating <= MAX_RATING;
	}

}
