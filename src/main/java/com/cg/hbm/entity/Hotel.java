package com.cg.hbm.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST)
	private List<RoomDetails> roomDetailsList;
	
	@OneToMany(mappedBy = "hotel",cascade = CascadeType.PERSIST)
	private List<BookingDetails> bookingDetails;
	
	@OneToMany(mappedBy = "hotel",cascade = CascadeType.PERSIST)
	private Set<Review> reviews;
}
