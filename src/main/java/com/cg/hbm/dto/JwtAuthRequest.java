package com.cg.hbm.dto;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	private String username;
	
	private String password;

}
