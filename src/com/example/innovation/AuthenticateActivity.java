package com.example.innovation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.support.VisualView;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class AuthenticateActivity extends Activity {

	// 音频获取源
	private int audioSource = MediaRecorder.AudioSource.MIC;
	private static int[] sampleRateInHzS = {8000, 11025, 16000, 22050, 44100};
	private static int sampleRateInHz = 8000;
	private static int channelConfig = AudioFormat.CHANNEL_IN_MONO;
	private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	private int bufferSizeInBytes = 0;
	private static final String AudioName = "/sdcard/love.raw";
	private static final String NewAudioName = "/sdcard/new.wav";
	
	private AudioRecord audioRecord;
	private boolean isRecord = false;
	
	private Button Start;
	private Button Stop;
	private ImageButton Toggle;
	private TextView info = null;
	private VisualView vv;
	private String tag = "test";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authenticate);
		
		init();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void init() {
		isRecord = false;
		info = (TextView) this.findViewById(R.id.tTips);
		vv = (VisualView) this.findViewById(R.id.vv);
		createAudioRecord();		
	}
	
	private void createAudioRecord() {
		Log.v(tag, "createAudioRecord start");
		for (int i=sampleRateInHzS.length-1; i>=0; i--)
		{
			bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHzS[i], channelConfig, audioFormat);
			if (bufferSizeInBytes == AudioRecord.ERROR_BAD_VALUE)
				Log.v(tag, "error");
			audioRecord = new AudioRecord(audioSource, sampleRateInHzS[i], channelConfig, audioFormat, bufferSizeInBytes);
			if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
				sampleRateInHz = sampleRateInHzS[i];
				Log.v(tag, "success");
				break;
			}
			Log.v(tag, "continue");
		}
		if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
			Log.v(tag, "createAudioRecord end");
			this.info.setText("sampleRateInHz = " + sampleRateInHz);
		}
		else{
			Log.v(tag, "Initialized failed");
			this.info.setText("设备初始化失败");
			this.Start.setEnabled(false);
		}
	}
	
	class TestAudioListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING)
				stopRecord();
			else
				startRecord();
		}
	}
	
	private void startRecord() {
		Log.v(tag, "startRecord start");
		audioRecord.startRecording();
		Log.v(tag, "startRecording end");
		this.info.setText(R.string.s_StartAuthenticate);
		Log.v(tag, "info end");
		new Thread(new ViewThread()).start();
		new Thread(new AudioRecordThread()).start();
		Log.v(tag, "startRecord end");
	}
	
	private void stopRecord() {
		close();
		this.info.setText(R.string.s_StopAuthenticate);
	}
	
	private void close() {
		if (audioRecord != null) {
			System.out.println("Stop Record ...");
			audioRecord.stop();
			Log.v(tag, "stop end");
		}
		Log.v(tag, "close end");
	}

	class AudioRecordThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.v(tag, "AudioRecordThread start");
			writeDataToFile();
//			copyWaveFile(AudioName, NewAudioName);
			Log.v(tag, "AudioRecordThread end");
		}
	}

	class ViewThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.v(tag, "ViewThread start");
			while (!Thread.currentThread().isInterrupted()) { 
				try { 
				Thread.sleep(50); 
				} catch (InterruptedException e) { 
				Thread.currentThread().interrupt(); 
				} 
				vv.postInvalidate();
			}
			Log.v(tag, "ViewThread end");
		}
	}
	
	private void writeDataToFile() {
		byte[] audioData = new byte[bufferSizeInBytes];
//		FileOutputStream fos = null;
		int readsize = 0;
//		try {
//			File file = new File(AudioName);
//			if (file.exists()) {
//				file.delete();
//			}
//			fos = new FileOutputStream(file);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		while (isRecord == true) {
			readsize = audioRecord.read(audioData, 0, bufferSizeInBytes);
			if (AudioRecord.ERROR_INVALID_OPERATION != readsize) {
				for (int i=0; i<readsize; i++) {
					vv.pushdata(audioData[i]);
				}
//				try {
//					fos.write(audioData);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}
		
//		try {
//			fos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	private void copyWaveFile(String inFilename, String outFilename) {
		FileInputStream in = null;  
        FileOutputStream out = null;  
        long totalAudioLen = 0;  
        long totalDataLen = totalAudioLen + 36;  
        long longSampleRate = sampleRateInHz;  
        int channels = 2;  
        long byteRate = 16 * sampleRateInHz * channels / 8;  
        byte[] data = new byte[bufferSizeInBytes];  
        try {  
            in = new FileInputStream(inFilename);  
            out = new FileOutputStream(outFilename);  
            totalAudioLen = in.getChannel().size();  
            totalDataLen = totalAudioLen + 36;  
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,  
                    longSampleRate, channels, byteRate);  
            while (in.read(data) != -1) {  
                out.write(data);  
            }  
            in.close();  
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	private void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen, long totalDataLen, long longSampleRate, int channels, long byteRate) throws IOException {
		byte[] header = new byte[44];  
        header[0] = 'R'; // RIFF/WAVE header  
        header[1] = 'I';  
        header[2] = 'F';  
        header[3] = 'F';  
        header[4] = (byte) (totalDataLen & 0xff);  
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);  
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);  
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);  
        header[8] = 'W';  
        header[9] = 'A';  
        header[10] = 'V';  
        header[11] = 'E';  
        header[12] = 'f'; // 'fmt ' chunk  
        header[13] = 'm';  
        header[14] = 't';  
        header[15] = ' ';  
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk  
        header[17] = 0;  
        header[18] = 0;  
        header[19] = 0;  
        header[20] = 1; // format = 1  
        header[21] = 0;  
        header[22] = (byte) channels;  
        header[23] = 0;  
        header[24] = (byte) (longSampleRate & 0xff);  
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);  
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);  
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);  
        header[28] = (byte) (byteRate & 0xff);  
        header[29] = (byte) ((byteRate >> 8) & 0xff);  
        header[30] = (byte) ((byteRate >> 16) & 0xff);  
        header[31] = (byte) ((byteRate >> 24) & 0xff);  
        header[32] = (byte) (2 * 16 / 8); // block align  
        header[33] = 0;  
        header[34] = 16; // bits per sample  
        header[35] = 0;  
        header[36] = 'd';  
        header[37] = 'a';  
        header[38] = 't';  
        header[39] = 'a';  
        header[40] = (byte) (totalAudioLen & 0xff);  
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);  
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);  
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);  
        out.write(header, 0, 44);  
	}
	
	@Override
	protected void onDestroy() {
		close();
		audioRecord.release();
		audioRecord = null;
		super.onDestroy();
	}
	
}
