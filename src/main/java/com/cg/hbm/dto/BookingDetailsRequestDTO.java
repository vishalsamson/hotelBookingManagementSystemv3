package com.cg.hbm.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.cg.hbm.entity.Payment;

import lombok.Data;

@Data
public class BookingDetailsRequestDTO {
	
	@NotNull
    @Future
	private LocalDate bookedFrom;
	
	@NotNull
    @Future
	private LocalDate bookedTo;
	
	@Min(1)
	private int noOfAdults;
	
	@Min(0)
	private int noOfChildren;
	
	@Positive
	private double amount;
	
	@NotNull
	private Payment payment;

}
