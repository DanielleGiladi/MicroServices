package com.example.demo;

public class Customer {

	private Name name;
	private String email;
	private String birthdate;
	private String country;
	private String password;

	public Customer() {
		super();
	}

	public Customer(Name name, String email, String birthdate, String country, String password) {
		super();
		this.name = name;
		this.email = email;
		this.birthdate = birthdate;
		this.country = country;
		this.password = password;
	}

	public Customer(Customer customer) {
		this.name = customer.name;
		this.email = customer.email;
		this.birthdate = customer.birthdate;
		this.country = customer.country;
		this.password = "****";
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
