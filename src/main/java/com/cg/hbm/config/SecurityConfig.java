package com.cg.hbm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cg.hbm.security.CustomUserDetailService;
import com.cg.hbm.security.JwtAuthenticationEntryPoint;
import com.cg.hbm.security.JwtAuthenticationFilter;
import com.cg.hbm.service.impl.UserServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.cors().disable()
		.authorizeRequests()
		.antMatchers("/v3/api-docs", "/v2/api-docs",
	            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**").permitAll()
		.antMatchers("/public/**").permitAll()
		.antMatchers(HttpMethod.GET).permitAll()
		.antMatchers("/admin/**").hasAuthority("ADMIN")
		.antMatchers("/normaluser/**").hasAnyAuthority("NORMAL","ADMIN")
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//	@Autowired
//	private UserDetailsService userDetailService;
//	
//	@Autowired
//	JwtAuthenticationFilter jwtAuthenticationFilter;
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		System.out.println(" --->> configure HTTp method - start");
//		 http.
//		 //	csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//		 //	.and().
//		 	csrf().disable().cors().disable()
//			.authorizeRequests()
//
//			.antMatchers("/v3/api-docs", "/v2/api-docs",
//		            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**").permitAll()
//			.antMatchers("/public/**").permitAll()
////			.antMatchers("/admin/**").hasAuthority("ADMIN")
//			.antMatchers("/normaluser/**").permitAll()
//			.antMatchers(HttpMethod.GET).permitAll()
//			
//			.anyRequest().authenticated().and().
//			sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		 
//		 
//		 // ----- configure JWTFilters for all next request
//		 
//		 http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
//		 System.out.println(" --->> configure HTTp method - End");
//	} //end httpSecurity configuration
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	
//		System.out.println("=============>> inside security Config class - method auth manager builder "+auth);
//		auth.authenticationProvider(authenticationProvider());
//		//auth.userDetailsService(null)
//		
//	}
//
//	
//	@Bean
//	public PasswordEncoder passwordEncoder()
//	{
//		return NoOpPasswordEncoder.getInstance();
//		//return new BCryptPasswordEncoder();
//	}
//	
//	
//	@Bean
//    public UserDetailsService userDetailsService() {
//        return new UserServiceImpl();
//    }
//	
//	 @Bean
//	 public DaoAuthenticationProvider authenticationProvider() {
//		 
//		
//		 
//	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//	        authProvider.setUserDetailsService(userDetailsService());
//	        authProvider.setPasswordEncoder(passwordEncoder());
//	        System.out.println("=============>> Inside Security Config class DAO auth provider "+authProvider);
//	        return authProvider;
//	 }
//	
//	 @Bean
//	 public AuthenticationManager authenticationManagerBean()throws Exception
//	 {
//		 System.out.println(" --->> Inside AuthenticationManager @Bean ");
//		 return super.authenticationManagerBean();
//	 }
//	 
//
//}//end class
