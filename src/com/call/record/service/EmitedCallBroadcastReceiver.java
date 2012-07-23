package com.call.record.service;

import com.call.record.activity.CallRecordStaticItems;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EmitedCallBroadcastReceiver extends BroadcastReceiver{

	private static final String PHONENUMBER = "The outcoming phone number is ";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		if (!CallRecordStaticItems.isRunning())
			return;
		
		Bundle extras = intent.getExtras(); //I don't really know what is the objective of this line.
		
		if(extras != null){
			
			String phoneNumber = extras.getString(Intent.EXTRA_PHONE_NUMBER);
			Log.d("OUTCOMING PHONE NUMBER ----------->", PHONENUMBER + phoneNumber);
			
		}
		
	}

}
