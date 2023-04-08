package com.ms.common.filter;//package com.ms.common.filter;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.common.dto.responseDto.ErrorResponseDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthanticationEntryPoint implements AuthenticationEntryPoint {

	Logger log = LoggerFactory.getLogger(JWTAuthanticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		ErrorResponseDto<?> errorResponseDto = new ErrorResponseDto<>("401", null, "Unauthorized");

		log.error("Unauthorized request : {} ", request.getRequestURI());
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		OutputStream out = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, errorResponseDto);
		out.flush();

	}

}