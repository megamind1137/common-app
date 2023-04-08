package com.ms.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ms.common.entity.User;
import com.ms.common.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

	Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user found"));
	}

}
