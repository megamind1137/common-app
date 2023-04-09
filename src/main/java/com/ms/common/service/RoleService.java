package com.ms.common.service;

import java.util.List;

import org.springframework.lang.NonNull;

import com.ms.common.dto.requestDto.RoleRequestDto;
import com.ms.common.entity.Role;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface RoleService {

	String saveRole(@Valid RoleRequestDto roleRequestDto, @NonNull HttpServletRequest request);

	List<Role> getAllRoles();

	void deleteRole(Integer id);

	void assignRole(Integer valueOf, Long valueOf2);

}
