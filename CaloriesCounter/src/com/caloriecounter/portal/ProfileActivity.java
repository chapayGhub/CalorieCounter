package com.caloriecounter.portal;

import com.example.caloriescounter.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ProfileActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
	}

	@Override
	public void onResume() {
		super.onResume();
		loadProfile();
	}

	public void onSaveClicked(View v) {
		saveProifle();
		Toast.makeText(getApplicationContext(),
				getString(R.string.ui_profile_toast_save_text),
				Toast.LENGTH_SHORT).show();
	}

	public void onCancelClicked(View v) {
		Toast.makeText(getApplicationContext(),
				getString(R.string.ui_profile_toast_cancel_text),
				Toast.LENGTH_SHORT).show();

	}

	private void loadProfile() {
		String key, str_val;
		int int_val;

		key = getString(R.string.preference_name);
		SharedPreferences prefs = getSharedPreferences(key, MODE_PRIVATE);

		key = getString(R.string.preference_key_profile_name);
		str_val = prefs.getString(key, "");
		((EditText) findViewById(R.id.editName)).setText(str_val);

		key = getString(R.string.preference_key_profile_age);
		str_val = prefs.getString(key, "");
		((EditText) findViewById(R.id.editAge)).setText(str_val);

		key = getString(R.string.preference_key_profile_weight);
		str_val = prefs.getString(key, "");
		((EditText) findViewById(R.id.editWeight)).setText(str_val);
		key = getString(R.string.preference_key_profile_height);
		str_val = prefs.getString(key, "");
		((EditText) findViewById(R.id.editHeight)).setText(str_val);

		key = getString(R.string.preference_key_profile_gender);
		int_val = prefs.getInt(key, -1);
		if (int_val >= 0) {
			RadioButton radioBtn = (RadioButton) ((RadioGroup) findViewById(R.id.radioGender))
					.getChildAt(int_val);
			radioBtn.setChecked(true);
		}
	}

	private void saveProifle() {

		String key, str_val;
		int int_val;

		key = getString(R.string.preference_name);
		SharedPreferences prefs = getSharedPreferences(key, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		key = getString(R.string.preference_key_profile_name);
		str_val = ((EditText) findViewById(R.id.editName)).getText().toString();
		editor.putString(key, str_val);

		key = getString(R.string.preference_key_profile_age);
		str_val = ((EditText) findViewById(R.id.editAge)).getText().toString();
		editor.putString(key, str_val);

		key = getString(R.string.preference_key_profile_weight);
		str_val = ((EditText) findViewById(R.id.editWeight)).getText()
				.toString();
		editor.putString(key, str_val);
		key = getString(R.string.preference_key_profile_height);
		str_val = ((EditText) findViewById(R.id.editHeight)).getText()
				.toString();
		editor.putString(key, str_val);

		key = getString(R.string.preference_key_profile_gender);
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGender);
		int_val = radioGroup.indexOfChild(findViewById(radioGroup
				.getCheckedRadioButtonId()));
		editor.putInt(key, int_val);
		editor.apply();

	}

}