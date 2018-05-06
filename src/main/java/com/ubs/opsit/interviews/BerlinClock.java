package com.ubs.opsit.interviews;

import java.util.Collections;

import org.apache.commons.lang.StringUtils;

/**
 * Covert provided time from 24HH:MM:SS format to Berlin clock time format
 * @author Vinay Vishal
 */
public class BerlinClock implements TimeConverter{

	/**
	 * Convert provided time string into Berlin clock time format
	 * @param aTime : time string in 24HH:MM:SS format
	 * @return String representing Berlin Clock time 
	 */
	@Override
	public String convertTime(String aTime) {
		
		if(!isValidTime(aTime))
			throw new IllegalArgumentException(CommonConstant.EXCEPTION_NULL_EMPTY_TIME.getCode());
		
		String[] timeUnitToken = aTime.split(CommonConstant.DELIMETER_COLON.getCode(), 3);
		
		int hours = 0, minutes = 0, seconds = 0;
		
		try{
			
			hours	= Integer.parseInt(timeUnitToken[0]);
			minutes	= Integer.parseInt(timeUnitToken[1]);
			seconds	= Integer.parseInt(timeUnitToken[2]);
			
			if(!isValidHorMinSec(hours, minutes, seconds))
				throw new IllegalArgumentException(CommonConstant.EXCEPTION_NMBER_FORMAT.getCode());
			
		}catch(NumberFormatException nfe){
			throw new IllegalArgumentException(CommonConstant.EXCEPTION_NMBER_FORMAT.getCode());
		}
		
		/* Approach#1 */
		String convertedTime = convertTimeIntoBerlinClockFormat(hours, minutes, seconds);
		
		/* Approach#2 */
		//String convertedTime = convertTimeIntoBerlinClockFormatJava8(hours, minutes, seconds);
		
		/* Approach#3 */
		//String convertedTime = convertTimeIntoBerlinTimeFormat(hours, minutes, seconds);
		
		return convertedTime;
	}
	
	/**
	 * Convert provided time input into Berlin Clock time format
	 * @param hours		- provided hours value in input (value should be between 0 to 23)
	 * @param minutes	- provided minutes vale in input (value should be between 0 to 59)
	 * @param seconds	- provided minutes vale in input (value should be between 0 to 59)
	 * @return String Berlin clock time representation 
	 */
	private String convertTimeIntoBerlinClockFormat(int hours, int minutes, int seconds) {
		
		StringBuilder berlinTimeFormat = new StringBuilder();
		
		berlinTimeFormat.append((seconds % 2 == 0) ? BerlinLamp.SEC_LAMP_ON.getCode() : BerlinLamp.LAMP_OFF.getCode());
		berlinTimeFormat.append(System.getProperty(CommonConstant.NEW_LINE_SEPARATOR.getCode()));
		berlinTimeFormat.append(hoursTopLampsState(4, (hours / 5)));
		berlinTimeFormat.append(hoursLowerLampsState(4, (hours % 5)));
		berlinTimeFormat.append(minutesTopLampsState(11, (minutes / 5)));
		berlinTimeFormat.append(minutesLowerLampsState(4, (minutes % 5)));
		
		return berlinTimeFormat.toString();
	}
	
	/**
	 * Convert provided time input into Berlin Clock time format
	 * @param hours		- provided hours value in input (value should be between 0 to 23)
	 * @param minutes	- provided minutes vale in input (value should be between 0 to 59)
	 * @param seconds	- provided minutes vale in input (value should be between 0 to 59)
	 * @return String Berlin clock time representation 
	 */
	private String convertTimeIntoBerlinClockFormatJava8(int hours, int minutes, int seconds) {
		
		StringBuilder berlinTimeFormat = new StringBuilder();
		
		berlinTimeFormat.append((seconds % 2 == 0) ? BerlinLamp.SEC_LAMP_ON.getCode() : BerlinLamp.LAMP_OFF.getCode());
		berlinTimeFormat.append(System.getProperty(CommonConstant.NEW_LINE_SEPARATOR.getCode()));
		berlinTimeFormat.append(getLampState(BerlinLamp.HOUR_LAMP_ON, 4, (hours / 5), true));
		berlinTimeFormat.append(getLampState(BerlinLamp.HOUR_LAMP_ON, 4, (hours % 5), true));
		berlinTimeFormat.append(getLampState(BerlinLamp.MIN_LAMP_ON, 11, (minutes / 5), true));
		berlinTimeFormat.append(getLampState(BerlinLamp.MIN_LAMP_ON, 4, (minutes % 5), false));
		
		return berlinTimeFormat.toString();
	}
	
	private String convertTimeIntoBerlinTimeFormat(int hours, int minutes, int seconds){
		
		char[][] lampsState = {
			{'O'},
			{'O','O','O','O'},
			{'O','O','O','O'},
			{'O','O','O','O','O','O','O','O','O','O','O'},
			{'O','O','O','O'}
		};
		
		lampsState[0][0] = seconds % 2 == 0 ? BerlinLamp.SEC_LAMP_ON.getCharCode() : BerlinLamp.LAMP_OFF.getCharCode();
		updateLampState(lampsState, 1, (hours / 5), BerlinLamp.HOUR_LAMP_ON);
		updateLampState(lampsState, 2, (hours % 5), BerlinLamp.HOUR_LAMP_ON);
		updateLampState(lampsState, 3, (minutes / 5), BerlinLamp.MIN_LAMP_ON);
		updateLampState(lampsState, 4, (minutes % 5), BerlinLamp.MIN_LAMP_ON);
		
		String berlinTimeFormat = doFormatInBerlinClockRepresentation(lampsState);
		
		return berlinTimeFormat;
		 
	}

	/**
	 * Turn ON/OFF top row hour lamps and return hours representation of top row of hours lamps
	 * @param nLamp 		- total number of lamp in top row which represents Hours
	 * @param lampsToBeOn	- N number of lamps to be ON
	 * @return String 		- Hours representation of top row of hours lamps
	 */
	private String hoursTopLampsState(int nLamp, int lampsToBeOn) {
		
		StringBuilder hoursLampsState = new StringBuilder();
		for(int tLamp=0; tLamp<nLamp; tLamp++){
			if(tLamp < lampsToBeOn)
				hoursLampsState.append(BerlinLamp.HOUR_LAMP_ON.getCode());
			else
				hoursLampsState.append(BerlinLamp.LAMP_OFF.getCode());
		}
		
		hoursLampsState.append(System.getProperty(CommonConstant.NEW_LINE_SEPARATOR.getCode()));
		
		return hoursLampsState.toString();
	}
	
	/**
	 * Turn ON/OFF lower row hour lamps and return hours representation of lower row of hours lamps
	 * @param nLamp 		- total number of lamp in lower row which represents Hours
	 * @param lampsToBeOn	- N number of lamps to be ON
	 * @return String 		- Hours representation of lower row of hours lamps
	 */
	private String hoursLowerLampsState(int nLamp, int lampsToBeOn) {
		
		StringBuilder hoursLampsState = new StringBuilder();
		
		for(int tLamp=0; tLamp<nLamp; tLamp++){
			if(tLamp < lampsToBeOn)
				hoursLampsState.append(BerlinLamp.HOUR_LAMP_ON.getCode());
			else
				hoursLampsState.append(BerlinLamp.LAMP_OFF.getCode());
		}
		
		hoursLampsState.append(System.getProperty(CommonConstant.NEW_LINE_SEPARATOR.getCode()));
		
		return hoursLampsState.toString();
	}
	
	/**
	 * Turn ON/OFF top row minutes lamps and return hours representation of lower row of minutes lamps
	 * @param nLamp 		- total number of lamp in top row which represents minutes
	 * @param lampsToBeOn	- N number of lamps to be ON
	 * @return String 		- Hours representation of lower row of minutes lamps
	 */
	private String minutesTopLampsState(int nLamp, int lampsToBeOn) {

		StringBuilder minTopLampsState = new StringBuilder();
		
		for(int tLamp=0; tLamp<nLamp; tLamp++){
			if(tLamp < lampsToBeOn){
				if(tLamp == 2 || tLamp == 5 || tLamp == 8)
					minTopLampsState.append(BerlinLamp.HOUR_LAMP_ON.getCode());
				else
					minTopLampsState.append(BerlinLamp.MIN_LAMP_ON.getCode());
			}else{
				minTopLampsState.append(BerlinLamp.LAMP_OFF.getCode());
			}
		}
		
		minTopLampsState.append(System.getProperty(CommonConstant.NEW_LINE_SEPARATOR.getCode()));
		
		return minTopLampsState.toString();
	}
	
	/**
	 * Turn ON/OFF lower minute lamps and return hours representation of lower row of minutes lamps
	 * @param nLamp 		- total number of lamp in lower row which represents minutes
	 * @param lampsToBeOn	- N number of lamps to be ON
	 * @return String 		- Hours representation of lower row of minutes lamps
	 */
	private String minutesLowerLampsState(int nLamp, int lampsToBeOn) {
		
		StringBuilder minLoweLampsState = new StringBuilder();
		
		for(int tLamp=0; tLamp<nLamp; tLamp++){
			if(tLamp < lampsToBeOn)
				minLoweLampsState.append(BerlinLamp.MIN_LAMP_ON.getCode());
			else
				minLoweLampsState.append(BerlinLamp.LAMP_OFF.getCode());
		}
		
		return minLoweLampsState.toString();
	}
	
	/**
	 * Turn lamp ON/OFF based on provided time unit value (HH, MM) 
	 * @param lamp			- BerlinClock enum which represents lamp
	 * @param nLamp			- Total number of lamps for provided time unit 
	 * @param lampsToBeOn	- N lamps to be turned ON and rest turned to be OFF
	 * @return String Berlin clock time representation 
	 */
	private String getLampState(BerlinLamp lamp, int nLamp, int lampsToBeOn, boolean isNewLine){
		
		StringBuilder lampsState = new StringBuilder();
		
		lampsState.append(String.join(CommonConstant.EMPTY_STRING.getCode(), 
				Collections.nCopies(lampsToBeOn, lamp.getCode())));
		
		lampsState.append(String.join(CommonConstant.EMPTY_STRING.getCode(), 
				Collections.nCopies((nLamp - lampsToBeOn), BerlinLamp.LAMP_OFF.getCode())));
		
		if(nLamp == 11)
			lampsState = new StringBuilder(lampsState.toString().replaceAll("YYY", "YYR"));
		
		if(isNewLine)
			lampsState.append(System.getProperty(CommonConstant.NEW_LINE_SEPARATOR.getCode()));
			
		return lampsState.toString();
	}
	
	private void updateLampState(char[][] lamps, int lampIndex, int lampsToBeOn, BerlinLamp lamp) {
		for(int tLamp=0; tLamp<lamps[lampIndex].length; tLamp++){
			if(tLamp < lampsToBeOn)
				if(lamp.getCode().equalsIgnoreCase("R"))
					lamps[lampIndex][tLamp] = BerlinLamp.HOUR_LAMP_ON.getCharCode();
				else if(lamp.getCode().equalsIgnoreCase("Y"))
					if(lampIndex == 3 && (tLamp == 2 || tLamp == 5 || tLamp == 8))
						lamps[lampIndex][tLamp] = BerlinLamp.HOUR_LAMP_ON.getCharCode();
					else
						lamps[lampIndex][tLamp] = BerlinLamp.MIN_LAMP_ON.getCharCode();
			else
				lamps[lampIndex][tLamp] = BerlinLamp.LAMP_OFF.getCharCode();
		}
	}
	
	private String doFormatInBerlinClockRepresentation(char[][] lamps) {
		
		StringBuilder berlinTimeFormat = new StringBuilder();
		
		for(int i=0; i<lamps.length; i++){
			berlinTimeFormat.append(new String(lamps[i]));
			if(i < 4)
				berlinTimeFormat.append(System.getProperty(CommonConstant.NEW_LINE_SEPARATOR.getCode()));
		}
		
		return berlinTimeFormat.toString();
	}

	public static void main(String[] args) {
		TimeConverter tc = new BerlinClock();
		System.out.print(tc.convertTime("23:59:59"));
	}
	
	/**
	 * Validate provided input string time, if null or empty returns false else true
	 * @param aTime	- provided time input value
	 * @return boolean valid or invalid input
	 */
	private boolean isValidTime(String aTime){
		
		if(StringUtils.isBlank(aTime))
			return false;
		
		String[] timeUnitToken = aTime.split(CommonConstant.DELIMETER_COLON.getCode(), 3);
		
		if(timeUnitToken.length != 3)
			return false;
		
		return true;
	}
	
	/**
	 * Validate Hours, Minutes and Seconds. If not in bond returns false else true.
	 * @param hours		- hours in provided time input
	 * @param minutes	- hours in provided time input
	 * @param seconds	- hours in provided time input
	 * @return boolean provided hours, minutes, seconds are valid and in bound returns true else false
	 */
	private boolean isValidHorMinSec(int hours, int minutes, int seconds){
		
		if(hours < 0 || hours > 24)
			return false;
		if(minutes < 0 || minutes > 59)
			return false;
		if(seconds < 0 || seconds > 59)
			return false;
		
		return true;
	}
	
}
