package com.cg.hbm.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.hbm.dto.UserRequestDTO;
import com.cg.hbm.dto.UserResponseDTO;
import com.cg.hbm.entity.User;
import com.cg.hbm.repository.IUserRepository;
import com.cg.hbm.service.IUserService;

@SpringBootTest
class UserServiceImplTest {
	
	@MockBean
	IUserRepository userRepository;
	
	@Autowired
	IUserService userService;
	
	
	@Autowired
	ModelMapper modelMapper;
	

	@Test
	void testGetUserById() {
		UserResponseDTO actualoutput=
				new UserResponseDTO(1, "vishal@test.com", "vishal@test.com", "Vish942", 965799077, "Solapur Maharashtra");
		
		UserResponseDTO demo=
				new UserResponseDTO(1, "vishal@test.com", "vishal@test.com", "Vish942", 965799077, "Solapur Maharashtra");
		
		
		User user=modelMapper.map(demo, User.class);
		
		Optional<User> user1=Optional.of(user);
		
		when(userRepository.findById(1)).thenReturn(user1);
		
		assertEquals(actualoutput, userService.getUserById(1));
	}
	
	@Test
	void testGetAllUsers() {
		List<UserResponseDTO> actualoutput=new ArrayList<>();
		actualoutput.add(new UserResponseDTO(1, "vishal@test.com", "vishal@test.com", "Vish942", 965799077, "Solapur Maharashtra"));
		actualoutput.add(new UserResponseDTO(2, "pratik@test.com", "pratik@test.com", "Prat942", 965799347, "Satara Maharashtra"));
		
		
		List<User> usersForTest=actualoutput.stream()
				.map(user->modelMapper.map(user, User.class))
				.collect(Collectors.toList());
		
		when(userRepository.findAll()).thenReturn(usersForTest);
		
		assertEquals(actualoutput, userService.getAllUsers());
	}
	
	

}
