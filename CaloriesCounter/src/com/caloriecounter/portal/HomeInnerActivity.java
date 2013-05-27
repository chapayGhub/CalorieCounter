package com.caloriecounter.portal;

import java.text.DecimalFormat;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.caloriecounter.utils.CalorieInput;
import com.example.caloriescounter.R;

public class HomeInnerActivity extends Activity {

	// private Spinner sp1;
	// private ArrayAdapter<String> adapter;
	// private List<String> type = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		ImageView img = (ImageView) findViewById(R.id.imageView2);
		TextView text = (TextView) findViewById(R.id.text_amount);
		text.setTextSize(20);
		Float calorie = this.getIntent().getFloatExtra("calories", 100);
		/**
		 * decide which activity to choose
		 */
		String[] foods = getResources().getStringArray(R.array.food_list);

		int index = (new Random()).nextInt(foods.length);
		/**
		 * get calorie burned in this activity temporarily hard code
		 */
		float amount = 0;
		try {
			int unit_cal = Integer.valueOf(this.getResources().getString(
					R.string.class.getField(foods[index]).getInt(null)));
			amount = 1.0f * calorie / unit_cal;

		} catch (Exception e) {
			Log.e("home", foods[index] + " calorie value not found!");
		}
		text.setText("Calories you burned= " + foods[index] + " x "
				+ (new DecimalFormat("##.##")).format(amount) + "!");

		try {
			img.setBackgroundResource(R.drawable.class.getField(foods[index])
					.getInt(null));
		} catch (Exception e) {
			Log.e("advisor", foods[index] + " does not exist in R.java");
		}

	}
}
