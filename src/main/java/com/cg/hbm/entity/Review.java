package com.cg.hbm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int reviewId;
	
	private String reviewContent;
	
	private double rating;
	
	@ManyToOne
	private Hotel hotel;
	
	@ManyToOne
	private User user;
}
