package com.example.demo;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveReviewsServiceImpl implements ReactiveReviewsService {
	private ReactiveReviewsCrud reviews;
	private AtomicLong atomicId;

	@Autowired
	public ReactiveReviewsServiceImpl(ReactiveReviewsCrud reviews) {
		super();
		this.reviews = reviews;
		this.atomicId = new AtomicLong(1L);
	}

	@Override
	@Transactional
	public Mono<Review> create(Review review) {
		review.setId(this.atomicId.getAndIncrement() + "");
		if (!CheckValidParameters.checkReview(review))
			throw new RuntimeException("Invalid review parameters");
		return this.reviews.save(review);
	}

	@Override
	@Transactional
	public Mono<Void> cleanup() {
		return this.reviews.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByMinRating(String productId, int value, Sort sort) {

		return this.reviews.findByProduct_idAndRatingGreaterThanEqual(productId, value, sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByMaxRating(String productId, int value, Sort sort) {
		return this.reviews.findByProduct_idAndRatingLessThanEqual(productId, value, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByTimeStampFrom(String productId, Date value, Sort sort) {
		return this.reviews.findByProduct_idAndReviewTimeStampAfter(productId, value, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByTimeStampTo(String productId, Date date, Sort sort) {
		return this.reviews.findByProduct_idAndReviewTimeStampBefore(productId, date, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByProduct(String productId, Sort sort) {
		return this.reviews.findByProduct_id(productId, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByReviewerByMinRating(String email, int value, Sort sort) {
		return this.reviews.findByCustomer_emailAndRatingGreaterThanEqual(email, value, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByReviewrByMaxRating(String email, int value, Sort sort) {
		return this.reviews.findByCustomer_emailAndRatingLessThanEqual(email, value, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByReviewerByTimeStampFrom(String email, Date date, Sort sort) {
		return this.reviews.findByCustomer_emailAndReviewTimeStampAfter(email, date, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByReviewerByTimeStampTo(String email, Date date, Sort sort) {
		return this.reviews.findByCustomer_emailAndReviewTimeStampBefore(email, date, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByReviewerByProduct(String email, Sort sort) {
		return this.reviews.findByCustomer_email(email, sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByRatingBetweenByTimeStampFrom(int minRatingInclusive, int maxRatingInclusive,
			Date date, Sort sort) {
		return this.reviews.findByRatingBetweenAndReviewTimeStampAfter(minRatingInclusive - 1, maxRatingInclusive + 1,
				date, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByRatingBetweenByTimeStampTo(int minRatingInclusive, int maxRatingInclusive,
			Date date, Sort sort) {
		return this.reviews.findByRatingBetweenAndReviewTimeStampBefore(minRatingInclusive - 1, maxRatingInclusive + 1,
				date, sort);

	}

	@Override
	@Transactional(readOnly = true)
	public Flux<Review> getAllReviewsByRatingBetween(int minRatingInclusive, int maxRatingInclusive, Sort sort) {
		return this.reviews.findByRatingBetween(minRatingInclusive - 1, maxRatingInclusive + 1, sort);

	}

}
