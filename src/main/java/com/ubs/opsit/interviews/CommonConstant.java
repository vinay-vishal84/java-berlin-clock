package com.ubs.opsit.interviews;

public enum CommonConstant {
	
	NEW_LINE_SEPARATOR("line.separator"),
	DELIMETER_COLON(":"),
	EXCEPTION_NULL_EMPTY_TIME("Provided time input is null or empty or invalid"),
	EXCEPTION_NMBER_FORMAT("Invalid time provided, expected time format is 24HH:MM:SS"),
	EMPTY_STRING(""),
	;
	
	
	private String code;
	
	CommonConstant(String code){
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
}
