package com.call.record.audio.record;

public interface RecordListenerInterface {

	/**
	 * This method starts the recording or AudioRecord. 
	 */
	void start();
	
	/**
	 * This method stops the recording of AudioRecord, sleeping it.//Vallecas english.
	 */
	void stop();
	
}
