package com.example.innovation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserSettingActivity extends Activity {

	private Button bModifyPassword = null;
	private Button bAdduser = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_setting);
		
		this.bModifyPassword = (Button)super.findViewById(R.id.b_ModifyPassword);
		this.bModifyPassword.setOnClickListener(new ModifyPasswordOnClickListenerImpl());
		
		this.bAdduser = (Button)super.findViewById(R.id.b_AddUser);
		this.bAdduser.setOnClickListener(new AddUserOnClickListenerImpl());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class ModifyPasswordOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(UserSettingActivity.this, ModifyPasswordActivity.class);
			UserSettingActivity.this.startActivity(it);
		}
		
	}
	
	private class AddUserOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(UserSettingActivity.this, AddUserActivity.class);
			UserSettingActivity.this.startActivity(it);
		}
		
	}
}
