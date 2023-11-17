package com.cg.hbm.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hbm.dto.HotelResponseDTO;
import com.cg.hbm.dto.ReviewDTO;
import com.cg.hbm.dto.ReviewRequestDTO;
import com.cg.hbm.dto.ReviewResponseDTO;
import com.cg.hbm.entity.Hotel;
import com.cg.hbm.entity.Review;
import com.cg.hbm.entity.User;
import com.cg.hbm.exception.ResourceNotFoundException;
import com.cg.hbm.repository.IHotelRepository;
import com.cg.hbm.repository.IReviewRepository;
import com.cg.hbm.repository.IUserRepository;
import com.cg.hbm.service.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService{
	
	@Autowired
	IReviewRepository reviewRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IHotelRepository hotelRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public ReviewResponseDTO createReview(ReviewRequestDTO reviewDto, int userId, int hotelId) {
		User user=userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		
		Hotel hotel=hotelRepository.findById(hotelId)
				.orElseThrow(()->new ResourceNotFoundException("Hotel","hotelId",hotelId));
		
		Review review=modelMapper.map(reviewDto, Review.class);
		
		review.setUser(user);
		review.setHotel(hotel);
		
		Review savedReview=reviewRepository.save(review);
		return modelMapper.map(savedReview, ReviewResponseDTO.class);
		
		
	}

	@Override
	public ReviewResponseDTO updateReview(ReviewDTO reviewDto, int reviewId) {
		Review review=reviewRepository.findById(reviewId)
				.orElseThrow(()->new ResourceNotFoundException("Review","reviewId",reviewId));
		
		review.setReviewContent(reviewDto.getReviewContent());
		review.setRating(reviewDto.getRating());
		
		Review savedReview=reviewRepository.save(review);
		return modelMapper.map(savedReview, ReviewResponseDTO.class);
	}

	@Override
	public void deleteReview(int reviewId) {
		Review review=reviewRepository.findById(reviewId)
				.orElseThrow(()->new ResourceNotFoundException("Review","reviewId",reviewId));
		reviewRepository.delete(review);
	}

	@Override
	public ReviewResponseDTO getReviewById(int reviewId) {
		Review review=reviewRepository.findById(reviewId)
				.orElseThrow(()->new ResourceNotFoundException("Review","reviewId",reviewId));
		return modelMapper.map(review, ReviewResponseDTO.class);
	}

	@Override
	public List<ReviewResponseDTO> getAllReviews() {
		List<Review> allReviews=reviewRepository.findAll();
		return allReviews.stream()
				.map(review->modelMapper.map(review, ReviewResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ReviewResponseDTO> getReviewsForHotel(int hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
	    Set<Review> reviews = hotel.getReviews();
	    return reviews.stream()
	            .map(review -> modelMapper.map(review, ReviewResponseDTO.class))
	            .collect(Collectors.toList());
	}

	@Override
	public double getAverageRatingForHotel(int hotelId) {
	    Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
	    Set<Review> reviews = hotel.getReviews();
	    if (reviews.isEmpty()) {
	        return 0.0;
	    }
	    double totalRating = reviews.stream().mapToDouble(Review::getRating).sum();
	    return totalRating / reviews.size();
	}
}
