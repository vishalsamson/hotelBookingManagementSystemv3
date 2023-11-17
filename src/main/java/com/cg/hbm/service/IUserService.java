package com.cg.hbm.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cg.hbm.dto.UserDTO;
import com.cg.hbm.dto.UserRequestDTO;
import com.cg.hbm.dto.UserResponseDTO;
import com.cg.hbm.entity.User;

@Service
public interface IUserService{
	
	UserResponseDTO registerNewUser(UserRequestDTO userDto);
	
	UserResponseDTO addUser(UserRequestDTO userDto);
	UserResponseDTO updateUser(UserRequestDTO userDto,int id);
	void removeUser(int id);
	UserResponseDTO getUserById(int userId);
	List<UserResponseDTO> getAllUsers();
}
