package com.ms.common.dto.responseDto;
public class AuthResponseDto {

	private String token;

	private UserResponseDto userResponseDto;

	public AuthResponseDto() {
		super();
	}

	public AuthResponseDto(String token, UserResponseDto userResponseDto) {
		super();
		this.token = token;
		this.userResponseDto = userResponseDto;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserResponseDto getUserResponseDto() {
		return userResponseDto;
	}

	public void setUserResponseDto(UserResponseDto userResponseDto) {
		this.userResponseDto = userResponseDto;
	}

}