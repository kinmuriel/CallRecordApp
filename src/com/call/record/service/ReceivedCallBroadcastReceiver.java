package com.call.record.service;

import com.call.record.activity.CallRecordStaticItems;
import com.call.record.activity.R;
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

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Bundle extras = arg1.getExtras(); //I don't really know what is the objective of this line.
		
		if(extras != null){
			
			String state = extras.getString(TelephonyManager.EXTRA_STATE); //Reading phone state.
			Log.d("PHONE STATE---------->", R.string.phoneState + state);
			
			if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
				
				//When a phone is received and call it's offhooked, application gets phone number and create
				//Recordlistener with it.
				String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER); 
				Log.d("PHONE NUMBER ----------->", R.string.phoneMsg + phoneNumber);
				
				RecordListenerInterface rli = new RecordListenerImpl(phoneNumber);
				CallRecordStaticItems.setRecordListener(rli);
				rli.start();
				
			}else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
				
				// if the phone state is idle, then the call is ended. In this case, 
				// the recordlistener ends and saves the record.
				RecordListenerInterface rli = CallRecordStaticItems.getRecordListener(); 
				if(rli != null)
					rli.stop();
				
			}
		}
	}

}
