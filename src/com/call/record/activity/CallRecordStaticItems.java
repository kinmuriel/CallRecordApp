package com.call.record.activity;

import com.call.record.audio.record.RecordListenerInterface;

public class CallRecordStaticItems {
	
	private static boolean stateRunning = false;
	private static RecordListenerInterface recordListener;
	private static boolean isIncomingCall;
	
	/**
	 * This method returns if the service has been initialized
	 * 
	 * @return stateRunning;
	 */
	public static boolean isRunning(){
		return stateRunning;
	}
	
	/**
	 * This method sets new state for Service.
	 * 
	 * @param r. Sets new value for stateRunning
	 */
	public static void setRunning(boolean r){
		stateRunning = r;
	}
	
	/**
	 * This methods sets a new RecordListener instance
	 * 
	 * @param rl. Sets new RecordListener, previously created.
	 */
	public static void setRecordListener(RecordListenerInterface rl){
		recordListener = rl;
	}
	
	/**
	 *  
	 * @return recordListener
	 */
	public static RecordListenerInterface getRecordListener(){
		return recordListener;
	}
	
	/**
	 * This method returns if have been an incoming call.
	 * 
	 * @return isIncomingCall
	 */
	public static boolean isIncomingCall(){
		return isIncomingCall;
	}
	
	/**
	 * Sets incomingCall value true if there is an incoming call. Else, it sets false
	 * 
	 * @param ic Incoming call or not
	 */
	public static void setIncomingCall(boolean ic){
		isIncomingCall = ic;
	}
	
	
}
