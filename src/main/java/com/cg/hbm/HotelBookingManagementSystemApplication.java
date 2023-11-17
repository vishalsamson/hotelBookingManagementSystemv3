package com.cg.hbm;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cg.hbm.entity.Role;
import com.cg.hbm.repository.IRoleRepository;
import com.cg.hbm.util.AppConstants;

@SpringBootApplication
@ComponentScan
public class HotelBookingManagementSystemApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IRoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingManagementSystemApplication.class, args);
		System.out.println("---------------Success---------------");
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));
		
		try {
			Role role1=new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setRoleName("ADMIN");
			
			Role role2=new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setRoleName("NORMAL");
			
			List<Role> roles= List.of(role1,role2);
			
			List<Role> result= this.roleRepository.saveAll(roles);
			
			result.forEach(r->{
				System.out.println(r.getRoleName());
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
