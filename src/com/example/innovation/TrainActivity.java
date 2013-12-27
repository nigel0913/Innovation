package com.example.innovation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TrainActivity extends Activity {
	
	private Button bTrainText = null;
	private Button bTrainCommand = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train);
		
		this.bTrainText = (Button)super.findViewById(R.id.b_TrainText);
		this.bTrainText.setOnClickListener(new TrainTextOnClickListenerImpl());
		
		this.bTrainCommand = (Button)super.findViewById(R.id.b_TrainCommand);
		this.bTrainCommand.setOnClickListener(new TrainCommandOnClickListenerImpl());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class TrainTextOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent it = new Intent(TrainActivity.this, TrainTextActivity.class);
			TrainActivity.this.startActivity(it);
		}
		
	}
	
	private class TrainCommandOnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent it = new Intent(TrainActivity.this, TrainCommandActivity.class);
			TrainActivity.this.startActivity(it);
		}
		
	}
}
