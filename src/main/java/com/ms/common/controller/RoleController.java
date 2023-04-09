package com.ms.common.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.common.dto.requestDto.RoleRequestDto;
import com.ms.common.dto.responseDto.ResponseDto;
import com.ms.common.dto.responseDto.SuccessResponseDto;
import com.ms.common.entity.Role;
import com.ms.common.service.RoleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/role")
@CrossOrigin
public class RoleController {

	Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@PostMapping("/save")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
	public ResponseDto<?> saveRole(@RequestBody @Valid RoleRequestDto roleRequestDto,
			@NonNull HttpServletRequest request) {

		String roleName = roleService.saveRole(roleRequestDto, request);

		log.info("Role save Successfully");
		return new SuccessResponseDto<>(roleName, "Role save Successfully", HttpStatus.CREATED);
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
	public ResponseDto<?> getAllRoles() {
		List<Role> rolesList = roleService.getAllRoles();

		log.info("Fetch Successfully");
		return new SuccessResponseDto<>(rolesList, "Fetch Successfully", Long.valueOf(rolesList.size()));
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
	public ResponseDto<?> deleteRole(@PathVariable("id") String id) {

		roleService.deleteRole(Integer.valueOf(id));

		log.info("Role delete successfully");
		return new SuccessResponseDto<>("Role delete successfully", Long.valueOf(id));
	}

	@PostMapping("assign")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
	public ResponseDto<?> assignRole(@RequestParam("roleId") String roleId, @RequestParam("userId") String userId) {

		roleService.assignRole(Integer.valueOf(roleId), Long.valueOf(userId));

		log.info("Role assign successfully");
		return new SuccessResponseDto<>(null, "Role assign successfully", Long.valueOf(userId));
	}

}
