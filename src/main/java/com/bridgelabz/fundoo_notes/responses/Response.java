package com.bridgelabz.fundoo_notes.responses;

import lombok.Data;

@Data
public class Response {

	private String message;
	
	private int statusCode;
	
	private Object obj;
	
	public Response(String message, int statusCode) {
		
		this.message = message;
		
		this.statusCode = statusCode;
	}

	public Response(String message, int statusCode, Object obj) {
		this.message = message;
		this.statusCode = statusCode;
		this.obj=obj;
	}
	

}
