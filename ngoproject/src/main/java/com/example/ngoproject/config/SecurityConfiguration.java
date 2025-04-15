package com.example.ngoproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Bean
	 PasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterCHain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").hasAuthority("ADMIN")
				                               .requestMatchers("/donations").authenticated()
				                               .anyRequest().permitAll()
				)
		.formLogin(form -> form.loginPage("/login")
				               .defaultSuccessUrl("/", true)
				               .permitAll()
				               )
		.logout(logout -> logout.logoutUrl("/logout")
				                .logoutSuccessUrl("/login?logout")
				                .permitAll()
				                );
		
		return http.build();
	}
	
	
}
