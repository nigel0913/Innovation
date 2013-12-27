package com.example.innovation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private String m_password;
	private String testtag = "test";
	
	private Button m_Commit;
	private EditText m_EditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_password);
		
		m_Commit = (Button) this.findViewById(R.id.b_Login);
		m_Commit.setOnClickListener(new LoginOnClickListener());
		
		m_EditText = (EditText) this.findViewById(R.id.edPassword);
		
		Init();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void Init() {
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.s_settingsPreferences), Context.MODE_PRIVATE);
		String key = getString(R.string.s_settingsPasswordKey);
		// set default password = 0000
		if (!sharedPref.contains(key)) {
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(key, "0000");
			editor.commit();
		}
	}
	
	class LoginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SharedPreferences sharedPref = getSharedPreferences(getString(R.string.s_settingsPreferences), Context.MODE_PRIVATE);
			String key = getString(R.string.s_settingsPasswordKey);
			String pwd = sharedPref.getString(key, "0000");
			if (m_EditText.getText().toString().equals(pwd)) {
				Log.v(testtag, "in");
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				LoginActivity.this.startActivity(intent);
				finish();
			}
			else {
				Toast.makeText(LoginActivity.this, "√‹¬Î¥ÌŒÛ", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
}
