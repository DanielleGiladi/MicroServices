package com.example.demo;

public class Name {

	private String first;
	private String last;
	
	public Name() {
	}
	
	public Name(String first, String last) {
		super();
		this.first = first;
		this.last = last;
	}
	
	
	public String getFirst() {
		return first;
	}
	public void setFirst(String firstname) {
		this.first = firstname;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String lastname) {
		this.last = lastname;
	}
	
	
}
