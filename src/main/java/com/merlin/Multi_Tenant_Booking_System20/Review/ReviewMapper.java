package com.merlin.Multi_Tenant_Booking_System20.Review;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewMapper {

    public Review toReview(ReviewDto dto){
        Review review = new Review();
        review.setMessage(dto.message());
        review.setRating(dto.rating());
        review.setCreatedAt(LocalDateTime.now());
        return review;
    }

    public ReviewResponseDto toReviewResponseDto(Review review){
        return new ReviewResponseDto(review.getId(),  review.getMessage(), review.getRating(),review.getVendor().getVendorId(),review.getClient().getUserId(),review.getBooking().getId());
    }
}
