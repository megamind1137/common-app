package com.ms.common.exception;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

	private String errorCode;
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	private Exception ex;
	private Object data;

	public ServiceException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public ServiceException(String errorCode, Object data) {
		super();
		this.errorCode = errorCode;
		this.data = data;
	}

	public ServiceException(String errorCode, HttpStatus httpStatus) {
		super();
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

	public ServiceException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public ServiceException(String errorCode, HttpStatus httpStatus, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

	public ServiceException(String errorCode, HttpStatus httpStatus, Exception ex) {
		super();
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.ex = ex;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Exception getEx() {
		return ex;
	}

	public void setEx(Exception ex) {
		this.ex = ex;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}