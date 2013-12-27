package com.support;

import com.example.innovation.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SecurityDialogFragment extends DialogFragment {
	
	SecurityDialogListener mListener;
	
	public interface SecurityDialogListener {
		public void onDialogSingleChoiceItemsClick(DialogFragment dialog, int item);
		public void onDialogPositiveClick(DialogFragment dialog);
//        public void onDialogNegativeClick(DialogFragment dialog);
	}

	@Override
	public Dialog onCreateDialog(Bundle savdInstanceState) {
		final CharSequence[] items = {"高", "中", "低"};   
		SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.s_settingsPreferences), Context.MODE_PRIVATE);
		int selectItem = sharedPref.getInt(getString(R.string.s_settingsSecuity), 2);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  
		builder.setTitle("选择安全等级");  
		builder.setSingleChoiceItems(items, selectItem, new DialogInterface.OnClickListener() {  
		    public void onClick(DialogInterface dialog, int item) {  
		    	mListener.onDialogSingleChoiceItemsClick(SecurityDialogFragment.this, item);
		    }  
		})
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int id) {
				mListener.onDialogPositiveClick(SecurityDialogFragment.this);
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});  
		return  builder.create();  
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SecurityDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
}
