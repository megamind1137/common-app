package com.ms.common.service;

import java.util.List;

import com.ms.common.dto.requestDto.AuthRequestDto;
import com.ms.common.dto.requestDto.UserRequestDto;
import com.ms.common.dto.requestDto.UserUpdateRequestDto;
import com.ms.common.dto.responseDto.AuthResponseDto;
import com.ms.common.dto.responseDto.UserResponseDto;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UserService {

	AuthResponseDto login(AuthRequestDto authRequestDto, @Nonnull HttpServletRequest request);

	void addSuperUser();

	UserResponseDto adduser(@Valid UserRequestDto userRequestDto);

	UserResponseDto updateUser(@Valid UserUpdateRequestDto userUpdateRequestDto);

	UserResponseDto getUserById(Long valueOf, @NotNull HttpServletRequest request);

	List<UserResponseDto> getAllUser(Integer perPage, Integer page, @NotNull HttpServletRequest request);

	void deleteUser(Long valueOf, @NotNull HttpServletRequest request);


}
