package com.example.demo;

import java.util.Date;

import org.springframework.data.domain.Sort;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveReviewsService {
	public Mono<Review> create(Review review);

	public Mono<Void> cleanup();

	public Flux<Review> getAllReviewsByMinRating(String productId, int value, Sort sort);

	public Flux<Review> getAllReviewsByMaxRating(String productId, int value, Sort sort);

	public Flux<Review> getAllReviewsByTimeStampFrom(String productId, Date value, Sort sort);

	public Flux<Review> getAllReviewsByTimeStampTo(String productId, Date date, Sort sort);

	public Flux<Review> getAllReviewsByProduct(String productId, Sort sort);

	public Flux<Review> getAllReviewsByReviewerByMinRating(String email, int parseInt, Sort sort);

	public Flux<Review> getAllReviewsByReviewrByMaxRating(String email, int parseInt, Sort sort);

	public Flux<Review> getAllReviewsByReviewerByTimeStampFrom(String email, Date date, Sort sort);

	public Flux<Review> getAllReviewsByReviewerByTimeStampTo(String email, Date date, Sort sort);

	public Flux<Review> getAllReviewsByReviewerByProduct(String email, Sort sort);

	public Flux<Review> getAllReviewsByRatingBetweenByTimeStampFrom(int minRatingInclusive, int maxRatingInclusive,
			Date date, Sort sort);

	public Flux<Review> getAllReviewsByRatingBetweenByTimeStampTo(int minRatingInclusive, int maxRatingInclusive,
			Date date, Sort sort);

	public Flux<Review> getAllReviewsByRatingBetween(int minRatingInclusive, int maxRatingInclusive, Sort sort);
}
