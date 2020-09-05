package com.example.demo;

import java.util.Date;

import org.springframework.data.domain.Sort;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface ReactiveReviewsCrud extends ReactiveMongoRepository<Review, String> {

	Flux<Review> findByProduct_idAndRatingGreaterThanEqual(String productId, int value, Sort sort);

	Flux<Review> findByProduct_idAndRatingLessThanEqual(String productId, int value, Sort sort);

	Flux<Review> findByProduct_idAndReviewTimeStampAfter(String productId, Date value, Sort sort);

	Flux<Review> findByProduct_idAndReviewTimeStampBefore(String productId, Date date, Sort sort);

	Flux<Review> findByProduct_id(String productId, Sort sort);

	Flux<Review> findByCustomer_emailAndRatingGreaterThanEqual(String email, int value, Sort sort);

	Flux<Review> findByCustomer_emailAndRatingLessThanEqual(String email, int value, Sort sort);

	Flux<Review> findByCustomer_emailAndReviewTimeStampAfter(String email, Date date, Sort sort);

	Flux<Review> findByCustomer_emailAndReviewTimeStampBefore(String email, Date date, Sort sort);

	Flux<Review> findByCustomer_email(String email, Sort sort);

	Flux<Review> findByRatingBetween(int minRatingInclusive, int maxRatingInclusive, Sort sort);

	Flux<Review> findByRatingBetweenAndReviewTimeStampBefore(int minRatingInclusive, int maxRatingInclusive, Date date,
			Sort sort);

	Flux<Review> findByRatingBetweenAndReviewTimeStampAfter(int minRatingInclusive, int maxRatingInclusive, Date date,
			Sort sort);

}
