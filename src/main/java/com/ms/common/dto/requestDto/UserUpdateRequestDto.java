package com.ms.common.dto.requestDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserUpdateRequestDto {

	@NotEmpty
	private String id;

	@NotBlank
	@Size(min = 2)
	private String name;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(min = 10, max = 10)
	private String mobileNumber;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}