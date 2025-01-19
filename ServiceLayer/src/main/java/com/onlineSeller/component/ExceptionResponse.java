package com.onlineSeller.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ExceptionResponse {

	public ExceptionResponse(String string) {
		// TODO Auto-generated constructor stub
	}

	@NonNull
	private String message;
	
	private String details;
	
	@NonNull
	private String status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
