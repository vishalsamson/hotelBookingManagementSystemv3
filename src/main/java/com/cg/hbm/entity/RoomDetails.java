package com.cg.hbm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int roomId;
	
	@Column(unique = true)
	private String roomNo;
	private String roomType;
	private double ratePerDay;
	private boolean isavailable;
	
	//image name
	private String fileName;
	
	//image type
    private String fileType;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER )
	@JoinColumn(name = "hotel_id")
	@JsonIgnore
	private Hotel hotel;

}
