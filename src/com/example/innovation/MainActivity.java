package com.example.innovation;

import com.support.SecurityDialogFragment;

import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements SecurityDialogFragment.SecurityDialogListener{

	private Button bStartService = null;
	private Button bStopService = null;
	private Button bUserSetting = null;
	private Button bTrain = null;
	private Button bSecurityLevel = null;
	private Button bAppLock = null;
	private Button bTest = null;
	private Button bAvoidUnstall = null;
	private Button bSelectMusic = null;
	
	private String tag = "MainActivity";
	private int m_securityLevel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_securityLevel = 2;
		
		this.bUserSetting = (Button)super.findViewById(R.id.b_UserSetting);
		this.bUserSetting.setOnClickListener(new UserSettingOnClickListenerImpl());
		
		this.bTrain = (Button)super.findViewById(R.id.b_Train);
		this.bTrain.setOnClickListener(new TrainOnClickListenerImpl());
		
		this.bSecurityLevel = (Button)super.findViewById(R.id.b_SecurityLevel);
		this.bSecurityLevel.setOnClickListener(new SecurityOnClickListenerImpl());
		
		this.bAppLock = (Button)super.findViewById(R.id.b_AppLock);
		this.bAppLock.setOnClickListener(new AppListOnClickListenerImpl());
		
		this.bStartService = (Button)super.findViewById(R.id.b_StartService);
		this.bStartService.setOnClickListener(new StartServiceOnClickListenerImpl());
		
		this.bStopService = (Button)super.findViewById(R.id.b_CloseService);
		this.bStopService.setOnClickListener(new StopServiceOnClickListenerImpl());
		
		this.bTest = (Button)super.findViewById(R.id.b_Test);
		this.bTest.setOnClickListener(new TestOnClickListenerImpl());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class UserSettingOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(MainActivity.this, ModifyPasswordActivity.class);
			MainActivity.this.startActivity(it);
		}
		
	}
	
	private class TrainOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(MainActivity.this, TrainActivity.class);
			MainActivity.this.startActivity(it);
		}
		
	}
	
	private class SecurityOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			Intent it = new Intent(MainActivity.this, TrainActivity.class);
//			MainActivity.this.startActivity(it);
			showSecurityDialog();
		}
		
	}
	
	private class AppListOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(MainActivity.this, ApkListActivity.class);
			MainActivity.this.startActivity(it);
		}
		
	}
	
	private class StartServiceOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent service = new Intent(MainActivity.this, AuthenticateService.class);
			MainActivity.this.startService(service);
		}
		
	}
	
	private class StopServiceOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent service = new Intent(MainActivity.this, AuthenticateService.class);
			MainActivity.this.stopService(service);
		}
		
	}
	
	private class TestOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(MainActivity.this, RecordActivity.class);
			MainActivity.this.startActivity(it);
		}
		
	}
	
	public void showSecurityDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SecurityDialogFragment();
        dialog.show(getFragmentManager(), "SecurityDialogFragment");
    }

	@Override
	public void onDialogSingleChoiceItemsClick(DialogFragment dialog, int item) {
		// TODO Auto-generated method stub
		m_securityLevel = item;
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.s_settingsPreferences), Context.MODE_PRIVATE);
		String key = getString(R.string.s_settingsSecuity);
		int level = sharedPref.getInt(key, 2);
		// set default password = 0000
		if (level != m_securityLevel) {
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt(key, m_securityLevel);
			editor.commit();
		}
	}

}
