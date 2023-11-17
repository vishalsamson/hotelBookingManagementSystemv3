package com.cg.hbm.dto;

import com.cg.hbm.entity.Hotel;
import com.cg.hbm.entity.User;

import lombok.Data;

@Data
public class ReviewDTO {
	private int reviewId;
	private String reviewContent;
	private double rating;
	private Hotel hotel;
	private User user;
}
