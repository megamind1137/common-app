package com.ms.common.service;

import com.ms.common.dto.requestDto.AuthRequestDto;
import com.ms.common.dto.responseDto.AuthResponseDto;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	AuthResponseDto login(AuthRequestDto authRequestDto, @Nonnull HttpServletRequest request);

	void addSuperUser();

}
