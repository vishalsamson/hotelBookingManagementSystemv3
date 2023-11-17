package com.cg.hbm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequestDTO {
	@NotBlank
	private String city;
	
	@NotBlank
	private String hotelName;
	
	@NotBlank
	private String address;
	
	private String description;
	
	@Positive
	private double averageRatePerDay;
	
	@Email
	private String email;
	
	@Pattern(regexp = "\\d{10}")
	private String phone1;
	
	@Pattern(regexp = "\\d{10}")
	private String phone2;
	
	@Pattern(regexp = "www\\.[a-zA-Z0-9]+\\.[a-zA-Z]{2,}")
	private String website;
	
	private boolean isBooked;
}
