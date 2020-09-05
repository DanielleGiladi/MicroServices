package com.example.demo;

import java.util.List;

public interface CustomerService {

	// GET customer with email
	public Customer getCustomerWithEmail(String email);

	// GET customer with email
	public Customer getCustomerWithEmailAndPassword(String email, String password);

	// GET all Customers
	public List<Customer> getAllCustomers(int size, int page);

	// GET all Customers ByLastName
	public List<Customer> getAllCustomersByLastName(int size, int page, String lastName);

	// GET all Customers byLastNameBeginsWith
	public List<Customer> getAllCustomersByLastNameBeginsWith(int size, int page, String lastnameBeginning);

	// GET all Customers byCountry
	public List<Customer> getAllCustomersByCountry(int size, int page, String country);

	// GET all Customers byCountry
	public List<Customer> getAllCustomersByAgeOver(int size, int page, int minAgeExclusive);

	// POST new customers
	public Customer createCustomer(Customer newCustomer);

	// PUT customer with email
	public void update(String email, Customer update);

	// DELETE all
	public void deleteAll();
}
