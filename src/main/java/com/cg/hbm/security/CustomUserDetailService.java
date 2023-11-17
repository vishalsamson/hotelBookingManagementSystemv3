package com.cg.hbm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.hbm.entity.User;
import com.cg.hbm.exception.ResourceNotFoundException;
import com.cg.hbm.repository.IUserRepository;

@Service
@Primary
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// loading user from database by userName
				User user = this.userRepository.findByEmail(email)
						.orElseThrow(() -> new ResourceNotFoundException("User ", " email : " + email, 0));
				
				return user;

	}


}

