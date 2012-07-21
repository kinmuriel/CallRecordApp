package com.call.record.audio.record;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.os.Environment;
import android.util.Log;

public class RecordListenerImpl implements RecordListenerInterface{
	
	private final static int AUDIOSOURCE = AudioSource.VOICE_DOWNLINK;
	private final static int SAMPLERATEHZ = 8000;
	private final static int CHANNELCONFIG = AudioFormat.CHANNEL_IN_MONO;
	private final static int AUDIOFORMAT = AudioFormat.ENCODING_PCM_16BIT;
	private final static int BUFFERSIZE = AudioRecord.getMinBufferSize(SAMPLERATEHZ, CHANNELCONFIG, AUDIOFORMAT);
	private final static int BUFFERLENGTH = 1000000;
	
	private AudioRecord audioRecord;
	private boolean isRecording;
	private String name = "";
	
	public RecordListenerImpl() {
		// TODO Auto-generated constructor stub
		audioRecord = new AudioRecord(AUDIOSOURCE, SAMPLERATEHZ, CHANNELCONFIG,
				AUDIOFORMAT, BUFFERSIZE);
		//audioRecord = findAudioRecord();
		isRecording = false;
	}
	
	public RecordListenerImpl(String n){
		this();
		name = n;
	}
	
	/**
	 * private class responsible of doing the record
	 * 
	 * @author kinmuriel
	 *
	 */
	private class AudioRecordThread implements Runnable{

		private String getName(){
			
			Calendar cal1 = Calendar.getInstance();
			
			String fileName = cal1.get(Calendar.DATE) + "_"+cal1.get(Calendar.MONTH)
		    + "_" + (cal1.get(Calendar.YEAR) + 1) + "_" + cal1.get(Calendar.HOUR)
		    + "_" + cal1.get(Calendar.MINUTE);

			if (!name.equals("")){
				fileName += "_" + name;
			}
			return fileName;
		}
		
		public void run() {
			// TODO Auto-generated method stub
			byte[] buffer = new byte[BUFFERLENGTH];
			int read;
			DataOutputStream dos;
			
			try {
				Log.d("NAME", getName());
				dos = new DataOutputStream(new FileOutputStream
						(Environment.getExternalStorageDirectory() + "/" + getName()));
				
				while(isRecording){
					read = audioRecord.read(buffer, 0, BUFFERLENGTH);
					if (read != AudioRecord.ERROR_BAD_VALUE && read != AudioRecord.ERROR_INVALID_OPERATION){
						Log.d("RECORDING", "Recording " + read + " bytes");
						dos.write(buffer, 0, read);
					}
				}
				dos.flush();
				dos.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("EXCEPTION", e.getMessage());
			}		
		}
		
	}
	
	public void start() {
		// TODO Auto-generated method stub
		Log.d("START", "START action has taken place");
		if (!isRecording){
			isRecording = !isRecording;
			audioRecord.startRecording();
			Thread art = new Thread(new AudioRecordThread());
			art.start();
		}else{
			//This shouldn't take place.
			Log.e("BAD INITIALIZATION", "AudioRecord started with not null value");
		}
	}

	public void stop() {
		// TODO Auto-generated method stub
		Log.d("STOP", "STOP action has taken place");
		if(isRecording){
			isRecording = !isRecording;
		}else{
			//This shouldn't take place.
			Log.e("BAD INITIALIZATION", "AudioRecord have just been trying " +
					"stopped but it wasn't initialized");
		}
	}
}
