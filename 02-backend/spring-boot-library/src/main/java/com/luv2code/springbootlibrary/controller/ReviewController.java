package com.luv2code.springbootlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springbootlibrary.service.ReviewService;
import com.luv2code.springbootlibrary.service.Utils.ExtractJWT;

import requestmodels.ReviewRequest;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	private ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService=reviewService;
	}
	
	@PostMapping("/secure")
	public void postReview(@RequestHeader(value="Authorization") String token, @RequestBody ReviewRequest reviewRequest) throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		if(userEmail==null)
		{
			throw new Exception("User email is missing.");
		}
		reviewService.postReview(userEmail, reviewRequest);
	}
	
	@GetMapping("/secure/user/book")
	public Boolean reviewBookByUser(@RequestHeader(value="Authorization") String token, @Param(value = "bookId") Long bookId) throws Exception {
		String userEmail=ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		if(userEmail==null) {
			throw new Exception("User email is missing!");
		}
		return reviewService.userReviewListed(userEmail, bookId);
	}
}
