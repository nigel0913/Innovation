package com.example.innovation;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AuthenticateService extends Service {

	private final String LOG_TAG = "service";
	private BroadcastReceiver receiverOff, receiverOn;
	private IntentFilter filterOff, filterOn;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate() {
		Log.d(LOG_TAG, "onCreate()");
		super.onCreate();
		registerIntentReceivers();
		
		Toast.makeText(getApplicationContext(), "声音认证服务已经启动", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "onDestroy()");
		super.onDestroy();
		Toast.makeText(getApplicationContext(), "声音认证服务已经关闭", Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub

		super.onStart(intent, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "onUnbind()");
		return super.onUnbind(intent);
	}
	
	private void registerIntentReceivers() {
		Log.d(LOG_TAG, "registerIntentReceivers()");
		filterOff = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		filterOn = new IntentFilter(Intent.ACTION_SCREEN_ON);
		receiverOff = new BroadcastReceiver() {
			@SuppressWarnings("deprecation")
			public void onReceive(Context context, Intent intent) {
				Log.d(LOG_TAG, "receive SCREEN_OFF");
				Intent startMain = new Intent(context, RecordActivity.class);
				startMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(startMain);
			}
		};
		receiverOn = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				Log.d(LOG_TAG, "receive SCREEN_ON");
				KeyguardManager.KeyguardLock kl;
				KeyguardManager km = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
				kl = km.newKeyguardLock("");
				kl.disableKeyguard();
			}
		};
		registerReceiver(receiverOn, filterOn);
		registerReceiver(receiverOff, filterOff);
		Log.d(LOG_TAG, "registerIntentReceivers end");
	}

}
