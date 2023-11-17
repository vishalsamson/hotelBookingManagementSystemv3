package com.cg.hbm.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hbm.dto.JwtAuthRequest;
import com.cg.hbm.dto.JwtAuthResponse;
import com.cg.hbm.dto.UserDTO;
import com.cg.hbm.dto.UserRequestDTO;
import com.cg.hbm.dto.UserResponseDTO;
import com.cg.hbm.entity.User;
import com.cg.hbm.exception.ApiException;
import com.cg.hbm.security.JwtTokenHelper;
import com.cg.hbm.service.IUserService;

@RestController
@RequestMapping("/public")
public class AuthController {
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		logger.info("Attempting to authenticate user: {}", request.getUsername());
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
//		response.setUser(this.mapper.map((User) userDetails, UserDTO.class));
		logger.info("User '{}' successfully authenticated and token generated.", request.getUsername());
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			logger.error("Invalid Details !!", e);
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}

	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> registerNewUser(@RequestBody UserRequestDTO userDto){
		logger.info("Received registration request for user: {}", userDto.getUsername());
		UserResponseDTO registeredUser=this.userService.registerNewUser(userDto);
		logger.info("User successfully registered: {}", registeredUser.getUsername());
		return new ResponseEntity<UserResponseDTO>(registeredUser,HttpStatus.CREATED);
		
	}

}
