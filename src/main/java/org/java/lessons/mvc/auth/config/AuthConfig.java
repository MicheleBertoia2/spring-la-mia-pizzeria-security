package org.java.lessons.mvc.auth.config;

import org.java.lessons.mvc.auth.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http)
		throws Exception {
			 
			http.authorizeHttpRequests()
		        .requestMatchers("/delete/**").hasAuthority("ADMIN")
		        .requestMatchers("/edit/**").hasAuthority("ADMIN")
		        .requestMatchers("/create").hasAuthority("ADMIN")
		        .requestMatchers("/pizze/offers/**").hasAuthority("ADMIN")
		        .requestMatchers("/offers/edit/**").hasAuthority("ADMIN")
		        .requestMatchers("/offers/delete/**").hasAuthority("ADMIN")
		        .requestMatchers("/ingredients/**").hasAuthority("ADMIN")
		        .requestMatchers("/**").hasAuthority("USER")
		        .and().formLogin().defaultSuccessUrl("/")
		        .and().logout();
			
			return http.build();
	}
	
	@Bean
	UserService userDetailsService() {
		return new UserService();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
   
      authProvider.setUserDetailsService(userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
    }
}
