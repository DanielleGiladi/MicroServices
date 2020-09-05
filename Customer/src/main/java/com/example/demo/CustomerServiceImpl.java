package com.example.demo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	private Map<String, Customer> db;

	public CustomerServiceImpl(Map<String, Customer> db) {
		super();
		this.db = Collections.synchronizedMap(new TreeMap<>());
	}

	@Override
	public Customer getCustomerWithEmail(String email) {
		if(db.containsKey(email)) {
			return new Customer(this.db.get(email));
		}
		else
			throw new CustomerNotFoundException("no customer with email: "+email);

	}

	@Override
	public Customer getCustomerWithEmailAndPassword(String email, String password) {
		
		if(db.containsKey(email)) {
			if(this.db.get(email).getPassword().equals(password))
				return new Customer(this.db.get(email));
			else
				throw new CustomerNotFoundException("wrong password");
		}
		else
			throw new CustomerNotFoundException("no customer with email: "+email);

	}

	@Override
	public List<Customer> getAllCustomers(int size, int page) {
		if (size <= 0) {
			throw new RuntimeException("size must be postive");
		}

		if (page < 0) {
			throw new RuntimeException("page must be non negative");
		}

		return IntStream.range(0, this.db.size()).skip(size * page).limit(size).mapToObj(i -> {
			Customer c = new Customer(this.db.get(this.db.keySet().toArray()[i]));
			return c;
		}).collect(Collectors.toList());
	}

	@Override
	public List<Customer> getAllCustomersByLastName(int size, int page, String lastName) {
		if (size <= 0) {
			throw new RuntimeException("size must be postive");
		}

		if (page < 0) {
			throw new RuntimeException("page must be non negative");
		}

		String lowerCaseLastName = lastName.toLowerCase();
		return IntStream.range(0, this.db.size()).skip(size * page).mapToObj(i -> {
			Customer c = new Customer(this.db.get(this.db.keySet().toArray()[i]));
			return c;
		}).filter(customer -> customer.getName().getLast().toLowerCase().equals(lowerCaseLastName)).limit(size)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> getAllCustomersByLastNameBeginsWith(int size, int page, String lastnameBeginning) {
		if (size <= 0) {
			throw new RuntimeException("size must be postive");
		}

		if (page < 0) {
			throw new RuntimeException("page must be non negative");
		}

		String lowerCaseLastnameBeginning = lastnameBeginning.toLowerCase();
		return IntStream.range(0, this.db.size()).skip(size * page).mapToObj(i -> {
			Customer c = new Customer(this.db.get(this.db.keySet().toArray()[i]));
			return c;
		}).filter(customer -> customer.getName().getLast().toLowerCase().startsWith(lowerCaseLastnameBeginning))
				.limit(size).collect(Collectors.toList());

	}

	@Override
	public List<Customer> getAllCustomersByCountry(int size, int page, String country) {
		if (size <= 0) {
			throw new RuntimeException("size must be postive");
		}

		if (page < 0) {
			throw new RuntimeException("page must be non negative");
		}

		if (!CheckValidParameters.checkCountry(country))
			throw new RuntimeException("Invalid country");

		return IntStream.range(0, this.db.size()).skip(size * page).mapToObj(i -> {
			Customer c = new Customer(this.db.get(this.db.keySet().toArray()[i]));
			return c;
		}).filter(customer -> customer.getCountry().equals(country)).limit(size).collect(Collectors.toList());
	}

	@Override
	public List<Customer> getAllCustomersByAgeOver(int size, int page, int minAgeExclusive) {
		if (size <= 0) {
			throw new RuntimeException("size must be positive");
		}

		if (page < 0) {
			throw new RuntimeException("page must be non negative");
		}
		
		if (minAgeExclusive < 0) {
			throw new RuntimeException("byAgeOver must be positive");
		}

		LocalDate date = LocalDate.now();
		int curYear = date.getYear();

		return IntStream.range(0, this.db.size()).skip(size * page).mapToObj(i -> {
			Customer c = new Customer(this.db.get(this.db.keySet().toArray()[i]));
			return c;
		}).filter(customer -> (curYear - Integer.parseInt(customer.getBirthdate().split("-")[2])) > minAgeExclusive)
				.limit(size).collect(Collectors.toList());
	}

	@Override
	public Customer createCustomer(Customer newCustomer) {

		if (this.db.containsKey(newCustomer.getEmail()))
			throw new RuntimeException("Customer already exists!");

		if (!CheckValidParameters.checkCustomer(newCustomer))
			throw new RuntimeException("invalid customer Details ");

		else
			this.db.put(newCustomer.getEmail(), newCustomer);

		return newCustomer;

	}

	
	@Override
	public void update(String email, Customer update) {
		if (!this.db.containsKey(email))
			throw new RuntimeException("Customer doesn't exists!");
		else {
			Customer c = this.db.get(email);
			if(CheckValidParameters.checkCustomerForUpdate(update)) {
				if(update.getName()!=null)
					c.setName(update.getName());
				if(update.getBirthdate()!=null)
					c.setBirthdate(update.getBirthdate());
				if(update.getCountry()!=null)
					c.setCountry(update.getCountry());
				if(update.getPassword()!=null) {
					c.setPassword(update.getPassword());
				}
			}else {
				throw new RuntimeException("invalid customer Details for update ");
			}

		}

	}

	@Override
	public void deleteAll() {
		this.db.clear();
	}

}
