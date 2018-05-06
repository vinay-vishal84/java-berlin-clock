package com.ubs.opsit.interviews;

public enum BerlinLamp {
	
	HOUR_LAMP_ON("R"),
	SEC_LAMP_ON("Y"),
	MIN_LAMP_ON("Y"),
	LAMP_OFF("O"),
	MIN_LAMP("Y"),
	HRS_LAMP("R")
	;
	
	private String code;
	
	private BerlinLamp(String code) {
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public char getCharCode(){
		return code.charAt(0);
	}
}
