package com.caloriecounter.portal;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.caloriecounter.utils.CalorieInput;
import com.example.caloriescounter.R;

public class AdvisorInnerActivity extends Activity {

	// private Spinner sp1;
	// private ArrayAdapter<String> adapter;
	// private List<String> type = new ArrayList<String>();

	private Button mButtonKnow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advisor);
		// type.add("Walking");
		// type.add("Running");
		// type.add("standing");
		// type.add("Sleeping");
		// type.add("Jumping");

		// sp1 = (Spinner) findViewById(R.id.sp1);
		// adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, type);
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// sp1.setAdapter(adapter);
		// sp1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// // TODO Auto-generated method stub
		// arg0.setVisibility(View.VISIBLE);
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// // TODO Auto-generated method stub
		// arg0.setVisibility(View.VISIBLE);
		// }
		// });
		ImageView img = (ImageView) findViewById(R.id.imageView1);
		TextView text = (TextView) findViewById(R.id.text_adivisor);
		text.setTextSize(20);
		Integer calorie = this.getIntent().getIntExtra("calories", 100);
		/**
		 * decide which activity to choose
		 */
		String[] activities = getResources().getStringArray(
				R.array.activity_type_items);

		int index = (new Random()).nextInt(3);
		/**
		 * get calorie burned in this activity temporarily hard code
		 */
		int mins = CalorieInput.getActivityMinutesByCalories(calorie,
				activities[index]);

		String textValue = null;
		if (mins >= 60) {
			textValue = String.valueOf(mins / 60) + " hours "
					+ String.valueOf(mins % 60) + " mins";
		} else {
			textValue = String.valueOf(mins) + " mins";
		}
		text.setText("Go " + activities[index] + " for " + textValue + "!");

		try {
			img.setBackgroundResource(R.drawable.class.getField(
					activities[index]).getInt(null));
		} catch (Exception e) {
			Log.e("advisor", activities[index] + " does not exist in R.java");
		}
	}
}
