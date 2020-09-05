package com.example.demo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {

	private String id;
	private Customer customer;
	private Product product;
	private int rating;
	private Date reviewTimeStamp;
	private ReviewContent reviewContent;

	public Review() {
	}

	public Review(String id, Customer customer, Product product, int rating, Date reviewTimeStamp,
			ReviewContent reviewContent) {
		super();
		this.id = id;
		this.customer = customer;
		this.product = product;
		this.rating = rating;
		this.reviewTimeStamp = reviewTimeStamp;
		this.reviewContent = reviewContent;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getReviewTimeStamp() {
		return reviewTimeStamp;
	}

	public void setReviewTimeStamp(Date reviewTimeStamp) {
		this.reviewTimeStamp = reviewTimeStamp;
	}

	public ReviewContent getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(ReviewContent reviewContent) {
		this.reviewContent = reviewContent;
	}

}
