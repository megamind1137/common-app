package com.ms.common.service;

import com.ms.common.entity.User;

public interface JWTService {

	String extractUsernameFromToken(String token);

	Boolean validateToken(String token, User user);

	String generateToken(String userName);

}
