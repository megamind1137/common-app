package com.ms.common.dto.responseDto;

import java.time.LocalDateTime;
import java.util.Set;

import com.ms.common.entity.Address;
import com.ms.common.entity.MediaProfile;
import com.ms.common.entity.Role;

public class UserResponseDto {

	private Long id;

	private String name;

	private String username;

	private String email;

	private String mobileNumber;

	private LocalDateTime createdAt;

	private String createdBy;

	private LocalDateTime updatedAt;

	private String updatedBy;

	private Boolean enabled;

	private Role role;

	private Set<MediaProfile> mediaProfiles;

	private Address address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<MediaProfile> getMediaProfiles() {
		return mediaProfiles;
	}

	public void setMediaProfiles(Set<MediaProfile> mediaProfiles) {
		this.mediaProfiles = mediaProfiles;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}