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

public class ModifyPasswordActivity extends Activity {

	private Button m_ModifyPwd;
	private EditText m_EditPwdOld;
	private EditText m_EditPwdNew;
	private EditText m_EditPwdComfirm;
	
	private String sPwdOld;
	private String sPwdNew;
	private String sPwdComfirm;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_password);
		
		m_ModifyPwd = (Button) this.findViewById(R.id.b_ModifyPassword);
		m_ModifyPwd.setOnClickListener(new ModifyOnClickListener());
		m_EditPwdOld = (EditText) this.findViewById(R.id.editText_PasswordOld);
		m_EditPwdNew = (EditText) this.findViewById(R.id.editText_PasswordNew);
		m_EditPwdComfirm = (EditText) this.findViewById(R.id.editText_Password_Comfirm);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class ModifyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SharedPreferences sharedPref = getSharedPreferences(getString(R.string.s_settingsPreferences), Context.MODE_PRIVATE);
			String key = getString(R.string.s_settingsPasswordKey);
			String pwd = sharedPref.getString(key, "0000");
			sPwdOld = m_EditPwdOld.getText().toString();
			sPwdNew = m_EditPwdNew.getText().toString();
			sPwdComfirm = m_EditPwdComfirm.getText().toString();
			
			if ( sPwdNew.equals(sPwdComfirm) ){
				if (sPwdOld.equals(pwd)) {
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString(key, m_EditPwdNew.getText().toString());
					editor.commit();
					Toast.makeText(ModifyPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
					finish();
				}
				else {
					Toast.makeText(ModifyPasswordActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
				}
			}
			else {
				Toast.makeText(ModifyPasswordActivity.this, "确认密码不一致", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
}
