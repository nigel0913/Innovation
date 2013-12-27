package com.example.innovation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class ApkListActivity extends Activity {
	PackageManager packageManager;
	ListView apkList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applist);
		
		packageManager = getPackageManager();
		List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		
		List<PackageInfo> packageListShow = new ArrayList<PackageInfo>();
		
		for(PackageInfo pi : packageList) {
			if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==0) {
				packageListShow.add(pi);
			}
		}
		
		apkList = (ListView) findViewById(R.id.applist);
		apkList.setAdapter(new ApkAdapter(this, packageListShow, packageManager));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
