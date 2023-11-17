package com.cg.hbm.dto;

import java.util.List;
import java.util.Set;

import com.cg.hbm.entity.BookingDetails;
import com.cg.hbm.entity.Review;
import com.cg.hbm.entity.RoomDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseDTO {
	
	private int hotelId;
	private String city;
	private String hotelName;
	private String address;
	private String description;
	private double averageRatePerDay;
	private String email;
	private String phone1;
	private String phone2;
	private String website;
	private boolean isBooked;
}
