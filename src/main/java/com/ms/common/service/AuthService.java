package com.ms.common.service;//package com.ms.common.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ms.common.entity.User;

public interface AuthService extends UserDetailsService {

	@Override
	User loadUserByUsername(String userName);

}
