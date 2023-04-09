package com.ms.common.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.common.dto.requestDto.UserRequestDto;
import com.ms.common.dto.requestDto.UserUpdateRequestDto;
import com.ms.common.dto.responseDto.ResponseDto;
import com.ms.common.dto.responseDto.SuccessResponseDto;
import com.ms.common.dto.responseDto.UserResponseDto;
import com.ms.common.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/user")
@Validated
@CrossOrigin
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseDto<?> addUser(@Valid @RequestBody UserRequestDto userRequestDto) {

		UserResponseDto userResponseDto = userService.adduser(userRequestDto);

		log.info("User added Successfully");
		return new SuccessResponseDto<>(userResponseDto, "User added Successfully", HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseDto<?> updateUser(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {

		UserResponseDto userResponseDto = userService.updateUser(userUpdateRequestDto);

		log.info("User updated Successfully");
		return new SuccessResponseDto<>(userResponseDto, "User updated Successfully", HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_EMPLOYEE')")
	public ResponseDto<?> getUserById(@PathVariable("id") String id, @NotNull HttpServletRequest request) {

		UserResponseDto userResponseDto = userService.getUserById(Long.valueOf(id), request);

		log.info("User Fetch successfully");
		return new SuccessResponseDto<>(userResponseDto, "User Fetch successfully", HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseDto<?> deleteUser(@PathVariable("id") String id, @NotNull HttpServletRequest request) {

		userService.deleteUser(Long.valueOf(id), request);

		log.info("Successfully detete");
		return new SuccessResponseDto<>("Successfully detete", Long.valueOf(id));
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_EMPLOYEE')")
	public ResponseDto<?> getAllUser(@RequestParam("perPage") Integer perPage, @RequestParam("page") Integer page,
			@NotNull HttpServletRequest request) {

		List<UserResponseDto> userResponseDtos = userService.getAllUser(perPage, page, request);

		log.info("Data fetch successfully");
		return new SuccessResponseDto<>(userResponseDtos, "Data fetch successfully",
				Long.valueOf(userResponseDtos.size()));
	}

}
