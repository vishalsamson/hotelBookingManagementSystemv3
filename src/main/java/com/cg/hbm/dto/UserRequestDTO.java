package com.cg.hbm.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
	
	@NotBlank
    @Size(min = 3, max = 50)
	private String username;
	
	@NotBlank
	@Email
	private String email;
	
	@Pattern(regexp = "^[A-Z][A-Za-z]{3}\\d{3}$")
	private String password;
	
	@NotBlank
	private long mobile;
	
	@NotBlank
	private String address;

}
