package com.cg.hbm.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bookingId;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private User user;
	
	
	private LocalDate bookedFrom;
	private LocalDate bookedTo;
	private int noOfAdults;
	private int noOfChildren;
	private double amount;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;
	
//	@OneToMany(cascade = CascadeType.PERSIST)
	//@JoinColumn(name = "room_id")
//	RoomDetails roomDetails;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<RoomDetails> roomDetailsList;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="payment_id")
	private Payment payment;
	
}
