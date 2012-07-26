package com.call.record.service;

import com.call.record.activity.CallRecordStaticItems;
import com.call.record.audio.record.RecordListenerImpl;
import com.call.record.audio.record.RecordListenerInterface;
import com.call.record.listeners.RecordingPhoneStateListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


//ampliamos la clase BroadcastReceiver
//el nombre de la clase, podrá ser utilizado en el archivo
//AndroidManifiest.xml

public class ReceivedCallBroadcastReceiver extends BroadcastReceiver{
	
	private static final String PHONENUMBER = "The incoming phone number is ";
	private static final String DETECTING_INCOMING_CALL = "Detecting if the call is incoming or outgoing";
	private static final String INCOMING_CALL_PROCESSING = "IncomingCall";
	
	private boolean isOutgoingCall(Bundle extras){
		
		Log.d("METHOD---------->", DETECTING_INCOMING_CALL);
		
		String phoneNumber = extras.getString(Intent.EXTRA_PHONE_NUMBER);
		
		return phoneNumber != null;
		
	}
	
	private void doIncomingCall(Context context, Bundle extras){
		
		// Next is sample code to start and stop a callRecord with an incoming call.
				// This code FAILS with and OUTCOMING call
		
		Log.d("METHOD---------->", INCOMING_CALL_PROCESSING);
		
		if(extras != null){
			
			String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			Log.d("INCOMING PHONE NUMBER ----------->", PHONENUMBER + phoneNumber);
			
			PhoneStateListener psl = new RecordingPhoneStateListener(phoneNumber);
			TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			telephony.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
		}
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (!CallRecordStaticItems.isRunning())
			return;
		
		//This is the schema that the service would follow.
		
		Bundle extras = intent.getExtras(); // We get the extras from intent. We'll use this
									// extras to get phone call number, phone state...among others.
		
		if (isOutgoingCall(extras)){
			doIncomingCall(context, extras);
		}
	}
}
