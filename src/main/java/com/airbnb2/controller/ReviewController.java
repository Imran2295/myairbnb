package com.airbnb2.controller;

import com.airbnb2.dto.ReviewDto;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.entity.Review;
import com.airbnb2.service.ReviewService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // http://localhost:8080/api/v2/reviews/addReview/1
    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<?> addReview(@AuthenticationPrincipal PropertyUserEntity user
    , @PathVariable Long propertyId
    , @RequestBody ReviewDto reviewDto){
        boolean review = reviewService.addReview(user, propertyId, reviewDto);
        if(review) {
            return new ResponseEntity<>("Review Added Successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Review Already Present" , HttpStatus.BAD_REQUEST);
    }

    // http://localhost:8080/api/v2/reviews/get-reviews
    @GetMapping("/get-reviews")
    public ResponseEntity<?> getAllReviewsOfUser(@AuthenticationPrincipal PropertyUserEntity user){

        List<Review> reviews = reviewService.getUsersAllReviews(user);

            return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


    // http://localhost:8080/api/v2/reviews/get-all-reviews/1
    @GetMapping("/get-all-reviews/{id}")
    public ResponseEntity<?> getALlReviews(@PathVariable Long id){
        List<ReviewDto> reviewDtos = reviewService.getAllReviews(id);

        return new ResponseEntity<>(reviewDtos , HttpStatus.OK);
    }
}


