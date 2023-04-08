package com.ms.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.common.dto.requestDto.AuthRequestDto;
import com.ms.common.dto.responseDto.AuthResponseDto;
import com.ms.common.dto.responseDto.ResponseDto;
import com.ms.common.dto.responseDto.SuccessResponseDto;
import com.ms.common.service.UserService;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/authenticate")
@Validated
public class AuthenticationRestController {

	@Autowired
	private UserService userService;

	@PostMapping("/auth")
	public ResponseDto<?> login(@Valid @RequestBody AuthRequestDto authRequestDto,
			@Nonnull HttpServletRequest request) {
		AuthResponseDto authResponseDto = userService.login(authRequestDto, request);
		return new SuccessResponseDto<>(authResponseDto, "Successfully Login", HttpStatus.OK);

	}

	@GetMapping("/addSuperUser")
	public String addSuperUser() {
		userService.addSuperUser();
		return "Super User added successfully";
	}
}
