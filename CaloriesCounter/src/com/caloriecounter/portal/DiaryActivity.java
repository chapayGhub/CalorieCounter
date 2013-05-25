package com.caloriecounter.portal;


import com.example.caloriescounter.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class DiaryActivity extends Activity  {	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_diary);		

	}
	public void onKnownClicked(View v) {
	// Save profile
	// Making a "toast" informing the user the profile is saved.
	Toast.makeText(getApplicationContext(),
			getString(R.string.ui_profile_toast_save_text),
			Toast.LENGTH_SHORT).show();

	}

public void onClearClicked(View v) {
	// Making a "toast" informing the user changes are canceled.
	Toast.makeText(getApplicationContext(),
			getString(R.string.ui_profile_toast_cancel_text),
			Toast.LENGTH_SHORT).show();

}
	
}