package com.example.demo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.regex.Pattern;

public class CheckValidParameters {

	public static boolean checkCustomer(Customer customer) {

		return checkEmail(customer.getEmail()) && checkName(customer.getName())
				&& checkBirthday(customer.getBirthdate()) && checkCountry(customer.getCountry())
				&& checkPassword(customer.getPassword());
	}
	
	
	public static boolean checkCustomerForUpdate(Customer customer) {
		boolean validData = true;
		if(customer.getName() !=null) {
			if(!checkName(customer.getName()))
				validData = false;
		}
		if(customer.getBirthdate()!=null) {
			if(!checkBirthday(customer.getBirthdate()))
				validData = false;
		}
		if(customer.getCountry() != null) {
			if(!checkCountry(customer.getCountry()))
				validData = false;
		}
		if(customer.getPassword()!=null) {
			if(!checkPassword(customer.getPassword()))
				validData = false;
		}
		return validData;
	}

	
	public static boolean checkEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();

	}

	public static boolean checkName(Name name) {
		if (!name.getFirst().isEmpty() && !name.getLast().isEmpty())
			if (name.getFirst().matches("[a-zA-Z]+") && name.getLast().matches("[a-zA-Z]+"))
				return true;

		return false;

	}

	public static boolean checkPassword(String password) {
		return password.length() >= 3;

	}

	public static boolean checkBirthday(String birthday) {

		DateFormat dtF = new SimpleDateFormat("dd-MM-yyyy");
		dtF.setLenient(false);
		try {

			dtF.parse(birthday);

		} catch (ParseException e) {

			return false;
		}
		return true;

	}

	public static boolean checkCountry(String country) {
		return country.matches("[A-Z]{2}");

	}

	

}
