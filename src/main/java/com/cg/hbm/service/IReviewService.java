package com.cg.hbm.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cg.hbm.dto.ReviewDTO;
import com.cg.hbm.dto.ReviewRequestDTO;
import com.cg.hbm.dto.ReviewResponseDTO;

@Service
public interface IReviewService {
	ReviewResponseDTO createReview(ReviewRequestDTO reviewDto,int userId,int hotelId);
	ReviewResponseDTO updateReview(ReviewDTO reviewDto,int reviewId);
	void deleteReview(int reviewId);
	ReviewResponseDTO getReviewById(int reviewId);
	List<ReviewResponseDTO> getAllReviews();
	List<ReviewResponseDTO> getReviewsForHotel(int hotelId);
	double getAverageRatingForHotel(int hotelId);

}
