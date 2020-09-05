package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveReviewsController {

	private enum Filter {
		BY_MIN_RATING, BY_MAX_RATING, BY_TIME_STAMP_FROM, BY_TIME_STAMP_TO, BY_ALL
	};

	private enum FilterBetween {
		BY_TIME_STAMP_FROM, BY_TIME_STAMP_TO, BY_ALL
	};

	private ReactiveReviewsService reviewsService;

	@Autowired
	public ReactiveReviewsController(ReactiveReviewsService reviewsService) {
		super();
		this.reviewsService = reviewsService;
	}

	@RequestMapping(path = "/reviews/byProduct/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Review> genericGetReviewsByProduct(@PathVariable("productId") String productId,
			@RequestParam(name = "filterType", required = false, defaultValue = "") Filter filterType,
			@RequestParam(name = "filterValue", required = false) String value,
			@RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
		switch (filterType) {
		case BY_MIN_RATING: {
			return this.reviewsService.getAllReviewsByMinRating(productId, Integer.parseInt(value),
					Sort.by(Direction.ASC, sortBy));

		}

		case BY_MAX_RATING: {
			return this.reviewsService.getAllReviewsByMaxRating(productId, Integer.parseInt(value),
					Sort.by(Direction.ASC, sortBy));
		}

		case BY_TIME_STAMP_FROM: {

			Date date = changeToDateFormat(value);
			return this.reviewsService.getAllReviewsByTimeStampFrom(productId, date, Sort.by(Direction.ASC, sortBy));
		}

		case BY_TIME_STAMP_TO: {
			Date date = changeToDateFormat(value);
			return this.reviewsService.getAllReviewsByTimeStampTo(productId, date, Sort.by(Direction.ASC, sortBy));
		}

		case BY_ALL: {
			return this.reviewsService.getAllReviewsByProduct(productId, Sort.by(Direction.ASC, sortBy));

		}

		default: {
			throw new RuntimeException("Invalid URL");
		}
		}

	}

	@RequestMapping(path = "/reviews", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Review> create(@RequestBody Review review) {
		return this.reviewsService.create(review);
	}

	@RequestMapping(path = "/reviews", method = RequestMethod.DELETE)
	public Mono<Void> cleanup() {
		return this.reviewsService.cleanup();
	}

	@RequestMapping(path = "/reviews/byReviewer/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Review> genericGetReviewsByReviewer(@PathVariable("email") String email,
			@RequestParam(name = "filterType", required = false, defaultValue = "") Filter filterType,
			@RequestParam(name = "filterValue", required = false) String value,
			@RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
		switch (filterType) {
		case BY_MIN_RATING: {
			return this.reviewsService.getAllReviewsByReviewerByMinRating(email, Integer.parseInt(value),
					Sort.by(Direction.ASC, sortBy));

		}

		case BY_MAX_RATING: {
			return this.reviewsService.getAllReviewsByReviewrByMaxRating(email, Integer.parseInt(value),
					Sort.by(Direction.ASC, sortBy));
		}

		case BY_TIME_STAMP_FROM: {

			Date date = changeToDateFormat(value);
			return this.reviewsService.getAllReviewsByReviewerByTimeStampFrom(email, date,
					Sort.by(Direction.ASC, sortBy));
		}

		case BY_TIME_STAMP_TO: {
			Date date = changeToDateFormat(value);
			return this.reviewsService.getAllReviewsByReviewerByTimeStampTo(email, date,
					Sort.by(Direction.ASC, sortBy));
		}

		case BY_ALL: {
			return this.reviewsService.getAllReviewsByReviewerByProduct(email, Sort.by(Direction.ASC, sortBy));

		}

		default: {
			throw new RuntimeException("Invalid URL");
		}
		}

	}

	@RequestMapping(path = "/reviews/byRatingBetween/{minRatingInclusive}/{maxRatingInclusive}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Review> genericGetReviewsByRatingBetween(@PathVariable("minRatingInclusive") int minRatingInclusive,
			@PathVariable("maxRatingInclusive") int maxRatingInclusive,
			@RequestParam(name = "filterType", required = false, defaultValue = "") FilterBetween filterType,
			@RequestParam(name = "filterValue", required = false) String value,
			@RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
		switch (filterType) {

		case BY_TIME_STAMP_FROM: {

			Date date = changeToDateFormat(value);
			return this.reviewsService.getAllReviewsByRatingBetweenByTimeStampFrom(minRatingInclusive,
					maxRatingInclusive, date, Sort.by(Direction.ASC, sortBy));
		}

		case BY_TIME_STAMP_TO: {
			Date date = changeToDateFormat(value);
			return this.reviewsService.getAllReviewsByRatingBetweenByTimeStampTo(minRatingInclusive, maxRatingInclusive,
					date, Sort.by(Direction.ASC, sortBy));
		}

		case BY_ALL: {
			return this.reviewsService.getAllReviewsByRatingBetween(minRatingInclusive, maxRatingInclusive,
					Sort.by(Direction.ASC, sortBy));

		}

		default: {
			throw new RuntimeException("Invalid URL");
		}
		}

	}

	public static Date changeToDateFormat(String date) {

		Date newDate = null;
		try {
			newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;

	}

}
