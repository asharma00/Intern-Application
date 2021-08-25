package com.intern.utility;

public class ResponseForOtp {
	private String message;
	private Object obj;	
	
	public ResponseForOtp(String message, Object obj) {
		super();
		this.message = message;
		this.obj = obj;
	}
	
	public ResponseForOtp() {
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}

}
