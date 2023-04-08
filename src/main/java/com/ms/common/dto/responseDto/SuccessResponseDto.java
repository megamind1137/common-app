package com.ms.common.dto.responseDto;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class SuccessResponseDto<T> implements ResponseDto<T> {

	private int status;
	private String message = null;
	private T data;
	private LocalDateTime timestamp;
	private String requestId;
	private Long numItems;

	public SuccessResponseDto() {
		super();
	}

	public SuccessResponseDto(String message) {
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	public SuccessResponseDto(T data) {
		this.data = data;
		this.message = "Success";
		this.timestamp = LocalDateTime.now();
	}

	public SuccessResponseDto(String message, String requestId) {
		this.message = message;
		this.status = HttpStatus.OK.value();
		this.requestId = requestId;
		this.timestamp = LocalDateTime.now();

	}

	public SuccessResponseDto(T data, Long requestId) {
		this.data = data;
		this.requestId = String.valueOf(requestId);
		this.message = "Success";
		this.timestamp = LocalDateTime.now();
	}

	public SuccessResponseDto(T data, String message, HttpStatus status) {
		this.message = message;
		this.data = data;
		this.timestamp = LocalDateTime.now();
		this.status = status.value();
	}

	public SuccessResponseDto(T data, String message, Long numItems) {
		this.data = data;
		this.message = message;
		this.numItems = numItems;
		this.timestamp = LocalDateTime.now();
		this.status = HttpStatus.OK.value();
	}

	public SuccessResponseDto(T data, String message, String requestId) {
		this.data = data;
		this.message = message;
		this.requestId = requestId;
		this.timestamp = LocalDateTime.now();
		this.status = HttpStatus.OK.value();
	}

	public SuccessResponseDto(T data, String message, Long numItems, String requestId) {
		this.data = data;
		this.message = message;
		this.numItems = numItems;
		this.requestId = requestId;
		this.timestamp = LocalDateTime.now();
	}

	public SuccessResponseDto(T data, String message, HttpStatus status, Long numItems, String requestId) {
		this.data = data;
		this.message = message;
		this.numItems = numItems;
		this.status = status.value();
		this.requestId = requestId;
		this.timestamp = LocalDateTime.now();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Long getNumItems() {
		return numItems;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setNumItems(Long numItems) {
		this.numItems = numItems;
	}

}