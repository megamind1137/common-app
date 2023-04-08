package com.ms.common.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ms.common.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {

	@Value("${spring.jwt.secret}")
	private String JWT_SECRET;

	@Value("${spring.jwt.jwtExpirationInMs}")
	private int JWT_EXPIRATION_TIME_IN_MILLISECONDS;

	@Override
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return tokenCreator(claims, userName);
	}

	public String tokenCreator(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME_IN_MILLISECONDS))
				.signWith(getSignedKey(), SignatureAlgorithm.HS256).compact();
	}

	@Override
	public String extractUsernameFromToken(String theToken) {
		return extractClaim(theToken, Claims::getSubject);
	}

	public Date extractExpirationTimeFromToken(String theToken) {
		return extractClaim(theToken, Claims::getExpiration);
	}

	@Override
	public Boolean validateToken(String theToken, User user) {
		final String userName = extractUsernameFromToken(theToken);
		return (userName.equals(user.getUsername()) && !isTokenExpired(theToken));
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignedKey()).build().parseClaimsJws(token).getBody();

	}

	private boolean isTokenExpired(String theToken) {
		return extractExpirationTimeFromToken(theToken).before(new Date());
	}

	private Key getSignedKey() {
		byte[] keyByte = Decoders.BASE64.decode(JWT_SECRET);
		return Keys.hmacShaKeyFor(keyByte);
	}

}
