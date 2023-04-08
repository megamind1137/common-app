package com.ms.common.dto.requestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleRequestDto {

	private Integer roleId;

	@NotBlank
	private String name;

	@NotBlank
	@Size(max = 100)
	private String description;

	public RoleRequestDto() {
		super();
	}

	public RoleRequestDto(Integer roleId, @NotBlank String name, @NotBlank String description) {
		super();
		this.roleId = roleId;
		this.name = name;
		this.description = description;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}