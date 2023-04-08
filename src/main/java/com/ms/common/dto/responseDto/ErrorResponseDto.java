package com.ms.common.dto.responseDto;
import java.util.Collections;
import java.util.List;

public class ErrorResponseDto<T> implements ResponseDto<T> {

	private String code = "";
	private T data;
	private List<String> message;
	private Long timestamp;
	private String requestId;

	public ErrorResponseDto() {
		super();
	}

	public ErrorResponseDto(String errorMessage) {
		this.message = Collections.singletonList(errorMessage);
		this.timestamp = System.currentTimeMillis();
	}

	public ErrorResponseDto(String errorCode, String errorMessage) {
		this.code = errorCode;
		this.message = Collections.singletonList(errorMessage);
		this.timestamp = System.currentTimeMillis();
	}

	public ErrorResponseDto(String code, T data, String errorMessage) {
		this.timestamp = System.currentTimeMillis();
		this.code = code;
		this.data = data;
		this.message = Collections.singletonList(errorMessage);
	}

	public ErrorResponseDto(String code, List<String> message) {
		this.timestamp = System.currentTimeMillis();
		this.code = code;
		this.message = message;
	}

	public ErrorResponseDto(List<String> message) {
		this.timestamp = System.currentTimeMillis();
		this.code = "400";
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}