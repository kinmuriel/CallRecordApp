package com.call.record.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CallRecordAppActivity extends Activity {
	
	private Button startButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        startButton = (Button)findViewById(R.id.startButton);
        if(CallRecordStaticItems.isRunning()){ //If there is a service running...
        	startButton.setText(R.string.stop);
        }else{									// if there isn't a service running...
        	startButton.setText(R.string.start);
        }
        
        startButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// Service will be started or stopped when this button will be clicked.
				if(CallRecordStaticItems.isRunning()){
					CallRecordStaticItems.setRunning(false);	// The service is stopped.
					startButton.setText(R.string.start);
					//CallRecordStaticItems.getRecordListener().stop();//Use this line to
															//record from microphone 
					
				}else{
					CallRecordStaticItems.setRunning(true);		// The service is started.
					startButton.setText(R.string.stop);	
					//CallRecordStaticItems.getRecordListener().start();//Use this line 
															//if you are recording from microphone.
				}
			}
		});
    }
}