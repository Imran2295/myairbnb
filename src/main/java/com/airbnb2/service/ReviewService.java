package com.airbnb2.service;

import com.airbnb2.dto.ReviewDto;
import com.airbnb2.entity.Property;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.entity.Review;
import com.airbnb2.exception.RecordNotFoundException;
import com.airbnb2.repository.PropertyRepository;
import com.airbnb2.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    private PropertyRepository propertyRepository;

    public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }


    public boolean addReview(PropertyUserEntity user, Long id, ReviewDto reviewDto) {
        // if review already existed
        Review review = reviewRepository.findReviewByPropertyIdAndUserId(id, user.getId());
        if (review != null) {
            return false;
        }
        // checking if property is present or not.
        Property property = propertyRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No property Exist"));

        Review review1 = new Review();
        review1.setContent(reviewDto.getContent());
        review1.setProperty(property);
        review1.setPropertyUserEntity(user);

        Review saved = reviewRepository.save(review1);
        return true;
    }


    public List<Review> getUsersAllReviews(PropertyUserEntity user) {

        List<Review> allReviewsByUserId = reviewRepository.findAllReviewsByUser(user);
        // List<ReviewDto> reviewDtoList = allReviewsByUserId.stream().map(n -> mapToReviewDto(n)).collect(Collectors.toList());
        if(allReviewsByUserId.isEmpty()){
            throw new RecordNotFoundException("No Review Are present");
        }
        return allReviewsByUserId;
    }

    public List<ReviewDto> getAllReviews(Long id) {
        List<Object[]> allReviewsObj = reviewRepository.findAllReviews(id);
        List<ReviewDto> allReviewDto = new ArrayList<>();

        for(Object[] reviews: allReviewsObj ){
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setFirstName((String) reviews[0]);
            reviewDto.setCountry((String) reviews[1]);
            reviewDto.setLocation((String) reviews[2]);
            reviewDto.setContent((String) reviews[3]);
            allReviewDto.add(reviewDto);
        }
        if(allReviewDto.isEmpty()){
            throw new RecordNotFoundException("No review Present");
        }
        return allReviewDto;

    }



    public Review mapToReview(ReviewDto reviewDto){
        Review review = new Review();
        review.setContent(reviewDto.getContent());
        return review;
    }

    public ReviewDto mapToReviewDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setContent(review.getContent());
        return reviewDto;
    }
}

