package com.cg.hbm.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsRequestDTO {
	
	@Pattern(regexp = "[A-Z]\\d{3}")
	private String roomNo;
	
	@Pattern(regexp = "^(Premium|Suites|Deluxe|Double Deluxe)$")
	private String roomType;
	
	@Positive
	private double ratePerDay;
	
	@NotNull
	private boolean isavailable;
	
	@NotBlank
	private String fileName;
	
	@NotBlank
    private String fileType;
}
