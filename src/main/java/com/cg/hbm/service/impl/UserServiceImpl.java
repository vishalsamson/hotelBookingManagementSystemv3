package com.cg.hbm.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.boot.spi.AdditionalJaxbMappingProducer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.hbm.dto.UserDTO;
import com.cg.hbm.dto.UserRequestDTO;
import com.cg.hbm.dto.UserResponseDTO;
import com.cg.hbm.entity.Role;
import com.cg.hbm.entity.User;
import com.cg.hbm.exception.ResourceNotFoundException;
import com.cg.hbm.repository.IRoleRepository;
import com.cg.hbm.repository.IUserRepository;
import com.cg.hbm.service.IUserService;
import com.cg.hbm.util.AppConstants;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public UserResponseDTO addUser(UserRequestDTO userDto){
		User user=modelMapper.map(userDto, User.class);
		User savedUser=userRepository.save(user);
		return modelMapper.map(savedUser, UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO updateUser(UserRequestDTO userDto, int id) {
		User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setMobile(userDto.getMobile());
		user.setAddress(userDto.getAddress());
		
		User savedUser=userRepository.save(user);
		return modelMapper.map(savedUser, UserResponseDTO.class);
		
		
	}

	@Override
	public void removeUser(int id) {
		User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		userRepository.delete(user);
		
	}

	@Override
	public UserResponseDTO getUserById(int userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		return modelMapper.map(user, UserResponseDTO.class);
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
		List<User> users=userRepository.findAll();
		return users.stream().map(user->modelMapper.map(user, UserResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public UserResponseDTO registerNewUser(UserRequestDTO userDto) {
		User user=modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//setting roles to the new Users
		Role role=  this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser=this.userRepository.save(user);
		
		return this.modelMapper.map(newUser, UserResponseDTO.class);

	}
	

}
