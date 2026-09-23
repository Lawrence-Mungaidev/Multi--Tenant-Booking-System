package com.merlin.Multi_Tenant_Booking_System20.Review;

import com.merlin.Multi_Tenant_Booking_System20.Booking.Booking;
import com.merlin.Multi_Tenant_Booking_System20.Booking.BookingRepository;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.User.Role;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final VendorRepository vendorRepository;
    private final BookingRepository bookingRepository;
    private final ReviewMapper reviewMapper;


    public ReviewResponseDto createReview(Long vendorId, Long bookingId, User authenticatedUser,ReviewDto dto){
        if(!authenticatedUser.getRole().equals(Role.CLIENT)){
            throw new BusinessRuleException("You are not allowed to make a review");
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFound("Vendor Not Found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFound("Booking Not Found"));

        if(!booking.getVendor().equals(vendor)){
            throw new BusinessRuleException("The booking doesn't much the vendor ");
        }

        if(!booking.isAttended()){
            throw new BusinessRuleException("You cannot make any review since you didn't attend");
        }

        if (ChronoUnit.DAYS.between(booking.getAttendedDate(), LocalDateTime.now()) > 30) {
            throw new BusinessRuleException("Review window has closed");
        }

        Review review = reviewMapper.toReview(dto);

        review.setVendor(vendor);
        review.setBooking(booking);
        vendor.setReviewCount(vendor.getReviewCount() + 1);
        review.setRating(dto.rating());

        double newAverage = ((vendor.getAverageRating() * vendor.getReviewCount()) + dto.rating()) / (vendor.getReviewCount() + 1);
        vendor.setAverageRating(newAverage);
        vendor.setReviewCount(vendor.getReviewCount() + 1);

        var savedReview = reviewRepository.save(review);

        return reviewMapper.toReviewResponseDto(savedReview);
    }

    public List<ReviewResponseDto> getReviews(Long vendorId){
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFound("Vendor Not Found"));

        List<Review> reviews = vendor.getReviews();

        return reviews
                .stream()
                .map(reviewMapper::toReviewResponseDto)
                .toList();
    }

    public ReviewResponseDto  getReviewById(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFound("Review Not Found"));

        return reviewMapper.toReviewResponseDto(review);
    }
}
