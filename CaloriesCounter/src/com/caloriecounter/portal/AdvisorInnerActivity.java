package com.caloriecounter.portal;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.caloriecounter.R;
import com.caloriecounter.utils.CalorieInput;

public class AdvisorInnerActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advisor);

		ImageView img = (ImageView) findViewById(R.id.imageView1);
		TextView text = (TextView) findViewById(R.id.text_adivisor);
		text.setTextSize(20);
		Integer calorie = this.getIntent().getIntExtra("calories", 100);
		/**
		 * decide which activity to choose
		 */
		String[] activities = getResources().getStringArray(
				R.array.activity_type_items);

		int index = (new Random()).nextInt(activities.length);

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
