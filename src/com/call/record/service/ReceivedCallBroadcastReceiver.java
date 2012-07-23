package com.call.record.service;

import com.call.record.activity.CallRecordStaticItems;
import com.call.record.audio.record.RecordListenerImpl;
import com.call.record.audio.record.RecordListenerInterface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


//ampliamos la clase BroadcastReceiver
//el nombre de la clase, podrá ser utilizado en el archivo
//AndroidManifiest.xml

public class ReceivedCallBroadcastReceiver extends BroadcastReceiver{
	
	private static final String PHONESTATE = "The phone state is ";
	private static final String PHONENUMBER = "The incoming phone number is ";
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		if (!CallRecordStaticItems.isRunning())
			return;
		
		/*This is the schema that the service would follow.
		
		if (isIncomingCall()){
			doIncomingCall();
		}else{
			doOutComingCall();
		}
		
		 */
		
		// Next is sample code to start and stop a callRecord with an incoming call.
		// This code FAILS with and OUTCOMING call
		
		Bundle extras = arg1.getExtras(); //I don't really know what is the objective of this line.
		
		if(extras != null){
			
			String state = extras.getString(TelephonyManager.EXTRA_STATE); //Reading phone state.
			Log.d("INCOMING PHONE STATE---------->", PHONESTATE + state);
			
			if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
				CallRecordStaticItems.setIncomingCall(true);
			}else if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && 
					CallRecordStaticItems.isIncomingCall()){
				
				//When a phone is received(isIncomingCall) and call it's offhooked, 
				//application gets phone number and creates Recordlistener with it.
				String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				Log.d("INCOMING PHONE NUMBER ----------->", PHONENUMBER + phoneNumber);
				
				RecordListenerInterface rli = new RecordListenerImpl(phoneNumber);
				CallRecordStaticItems.setRecordListener(rli);
				rli.start();
				
			}else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
				
				CallRecordStaticItems.setIncomingCall(false);
				
				// if the phone state is idle, then the call is ended. In this case, 
				// the recordlistener ends and saves the record.
				RecordListenerInterface rli = CallRecordStaticItems.getRecordListener(); 
				if(rli != null)
					rli.stop();
				
			}
		}
	}

}
