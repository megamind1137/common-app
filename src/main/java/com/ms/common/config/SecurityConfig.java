package com.ms.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ms.common.filter.JWTAuthanticationEntryPoint;
import com.ms.common.filter.JWTAuthenticationFilter;
import com.ms.common.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] SECURED_URLs = { "" };

	private static final String[] UN_SECURED_URLs = { "/authenticate/**", "/swagger-ui/**", "/resources/**",
			"/v3/api-docs/**", "/api/v1/user/save" };

	@Autowired
	private JWTAuthenticationFilter authenticationFilter;

	@Autowired
	private JWTAuthanticationEntryPoint jwtAuthanticationEntryPoint;

	@Autowired
	private AuthService authService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		var authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(authService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests().requestMatchers(UN_SECURED_URLs).permitAll().and()
				.authorizeHttpRequests().requestMatchers(SECURED_URLs).hasAuthority("ADMIN").anyRequest()
				.authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthanticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

}
