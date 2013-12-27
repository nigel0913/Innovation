package com.example.innovation;

import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ApkAdapter extends BaseAdapter {

	List<PackageInfo> packageList;
	Activity context;
	PackageManager packageManager;
	
	public ApkAdapter(Activity context, List<PackageInfo> packageList, PackageManager packageManager) {
		super();
		this.context = context;
		this.packageList = packageList;
		this.packageManager = packageManager;
	}
	
	private class ViewHolder {
		ImageView apkIcon;
		TextView apkName;
		TextView apkSimpleInfo;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return packageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return packageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		LayoutInflater inflater = context.getLayoutInflater();
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_app, null);
			holder = new ViewHolder();
			
			holder.apkName = (TextView) convertView.findViewById(R.id.app_name);
			holder.apkIcon = (ImageView) convertView.findViewById(R.id.app_icon);
			holder.apkSimpleInfo = (TextView) convertView.findViewById(R.id.app_simpleinfo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		PackageInfo packageInfo = (PackageInfo) getItem(position);
		Drawable appIcon = packageManager.getApplicationIcon(packageInfo.applicationInfo);
		String appName = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
		appIcon.setBounds(0, 0, 40, 40);
		holder.apkIcon.setImageDrawable(appIcon);
		holder.apkName.setText(appName);
		holder.apkSimpleInfo.setText("基本信息");
		return convertView;
	}

}
