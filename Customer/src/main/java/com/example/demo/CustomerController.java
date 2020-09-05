package com.example.demo;

import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	private CustomerService customers;

	@Autowired
	public CustomerController(CustomerService customers) {
		super();
		this.customers = customers;
	}

	@RequestMapping(path = "/customers/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getCustomer(@PathVariable("email") String email) {
		return this.customers.getCustomerWithEmail(email);
	}

	@RequestMapping(path = "/customers/login/{email}/{password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getCustomerLogin(@PathVariable("email") String email, @PathVariable("password") String password) {
		return this.customers.getCustomerWithEmailAndPassword(email, password);
	}

	@RequestMapping(path = "/customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer[] getAllCustomers(@RequestParam(name = "size", required = false, defaultValue = "20") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "byLastName", required = false, defaultValue = "") String lastname,
			@RequestParam(name = "byCountry", required = false, defaultValue = "") String country,
			@RequestParam(name = "byLastNameBeginsWith", required = false, defaultValue = "") String lastnameBeginning,
			@RequestParam(name = "byAgeOver", required = false) Integer minAgeExclusive) {

		if (!lastname.isEmpty() && country.isEmpty() && lastnameBeginning.isEmpty() && minAgeExclusive == null)
			return this.customers.getAllCustomersByLastName(size, page, lastname).toArray(new Customer[0]);
		
		else if (!lastnameBeginning.isEmpty() && country.isEmpty() && lastname.isEmpty() && minAgeExclusive == null)
			return this.customers.getAllCustomersByLastNameBeginsWith(size, page, lastnameBeginning)
					.toArray(new Customer[0]);
		
		else if (!country.isEmpty() && lastname.isEmpty() && lastnameBeginning.isEmpty() && minAgeExclusive == null)
			return this.customers.getAllCustomersByCountry(size, page, country).toArray(new Customer[0]);
		
		else if (minAgeExclusive !=null && country.isEmpty() && lastname.isEmpty() && lastnameBeginning.isEmpty())
			return this.customers.getAllCustomersByAgeOver(size, page, minAgeExclusive).toArray(new Customer[0]);
		
		else if(minAgeExclusive ==null && country.isEmpty() && lastname.isEmpty() && lastnameBeginning.isEmpty())
			return this.customers.getAllCustomers(size, page).toArray(new Customer[0]);
		
		else 
			throw new RuntimeException("To many argument");

	}


	@RequestMapping(path = "/customers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer createCustomer(@RequestBody Customer newCustomer) {
		return this.customers.createCustomer(newCustomer);
	}

	@RequestMapping(path = "/customers/{email}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void update(@PathVariable("email") String email, @RequestBody Customer update) {
		this.customers.update(email, update);
	}

	@RequestMapping(path = "/customers", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete() {
		this.customers.deleteAll();
		;
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, String> handleMessageNotFound(CustomerNotFoundException e) {
		String message = e.getMessage();
		if (message == null) {
			message = "Customer not found";
		}
		return Collections.singletonMap("error", message);
	}

}
