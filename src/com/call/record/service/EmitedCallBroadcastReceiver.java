package com.call.record.service;

import com.call.record.activity.CallRecordStaticItems;
import com.call.record.listeners.RecordingPhoneStateListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class EmitedCallBroadcastReceiver extends BroadcastReceiver{

	private static final String PHONENUMBER = "The outcoming phone number is ";
	
	private String getOutgoingNumber(Bundle extras){
		
		String pn = extras.getString(Intent.EXTRA_PHONE_NUMBER);
		Log.d("OUTCOMING PHONE NUMBER ----------->", PHONENUMBER + pn);
		
		return pn;
	}
	
	private void doOutgoingCallProcessing(Context context, String phoneNumber){
		
		PhoneStateListener psl = new RecordingPhoneStateListener(phoneNumber);
		TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		telephony.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
		
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		//If service is off, then don't execute anything.
		if (!CallRecordStaticItems.isRunning())
			return;
		
		Bundle extras = intent.getExtras(); // We use this to get extra information from intent.
		
		String phoneNumber = getOutgoingNumber(extras);
		
		if (phoneNumber != null){	//If everything is correct then we start outgoing call processing
			doOutgoingCallProcessing(context,phoneNumber);
		}else{
			Log.e("ERROR GETTING PHONE NUMBER", "There has been some mistake trying to get " +
					"phone number into an outgoing call");
		}
	}

}
