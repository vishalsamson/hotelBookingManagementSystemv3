package com.cg.hbm.dto;

import java.time.LocalDate;

import com.cg.hbm.entity.Payment;

import lombok.Data;

@Data
public class BookingDetailsResponseDTO {
	private int id;
	private LocalDate bookedFrom;
	private LocalDate bookedTo;
	private int noOfAdults;
	private int noOfChildren;
	private double amount;
	private Payment payment;
}
