package com.caloriecounter.portal;

import com.example.caloriescounter.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class AdvisorActivity extends Activity {

	private NumberPicker numpicker1;
	private NumberPicker numpicker2;
	private NumberPicker numpicker3;
	private NumberPicker numpicker4;
	private NumberPicker numpicker5;
	private NumberPicker numpicker6;
	private NumberPicker numpicker7;
	private NumberPicker numpicker8;
	private NumberPicker numpicker9;
	private NumberPicker numpicker10;
	private NumberPicker numpicker11;
	private NumberPicker numpicker12;

	private Button mButtonAdvisor;
	private Button mButtonReset;

	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advisor);
		System.out.println("tab2");

		mContext = this;

		String[] nums = new String[21];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = Integer.toString(i);
		}

		numpicker1 = (NumberPicker) findViewById(R.id.numberPicker1);
		numpicker1.setMaxValue(20);
		numpicker1.setMinValue(0);
		numpicker1.setWrapSelectorWheel(true);
		numpicker1.setDisplayedValues(nums);

		numpicker2 = (NumberPicker) findViewById(R.id.numberPicker2);
		numpicker2.setMaxValue(20);
		numpicker2.setMinValue(0);
		numpicker2.setWrapSelectorWheel(true);
		numpicker2.setDisplayedValues(nums);

		numpicker3 = (NumberPicker) findViewById(R.id.numberPicker3);
		numpicker3.setMaxValue(20);
		numpicker3.setMinValue(0);
		numpicker3.setWrapSelectorWheel(true);
		numpicker3.setDisplayedValues(nums);

		numpicker4 = (NumberPicker) findViewById(R.id.numberPicker4);
		numpicker4.setMaxValue(20);
		numpicker4.setMinValue(0);
		numpicker4.setWrapSelectorWheel(true);
		numpicker4.setDisplayedValues(nums);

		numpicker5 = (NumberPicker) findViewById(R.id.numberPicker5);
		numpicker5.setMaxValue(20);
		numpicker5.setMinValue(0);
		numpicker5.setWrapSelectorWheel(true);
		numpicker5.setDisplayedValues(nums);

		numpicker6 = (NumberPicker) findViewById(R.id.numberPicker6);
		numpicker6.setMaxValue(20);
		numpicker6.setMinValue(0);
		numpicker6.setWrapSelectorWheel(true);
		numpicker6.setDisplayedValues(nums);

		numpicker7 = (NumberPicker) findViewById(R.id.numberPicker7);
		numpicker7.setMaxValue(20);
		numpicker7.setMinValue(0);
		numpicker7.setWrapSelectorWheel(true);
		numpicker7.setDisplayedValues(nums);

		numpicker8 = (NumberPicker) findViewById(R.id.numberPicker8);
		numpicker8.setMaxValue(20);
		numpicker8.setMinValue(0);
		numpicker8.setWrapSelectorWheel(true);
		numpicker8.setDisplayedValues(nums);

		numpicker9 = (NumberPicker) findViewById(R.id.numberPicker9);
		numpicker9.setMaxValue(20);
		numpicker9.setMinValue(0);
		numpicker9.setWrapSelectorWheel(true);
		numpicker9.setDisplayedValues(nums);

		numpicker10 = (NumberPicker) findViewById(R.id.numberPicker10);
		numpicker10.setMaxValue(20);
		numpicker10.setMinValue(0);
		numpicker10.setWrapSelectorWheel(true);
		numpicker10.setDisplayedValues(nums);

		numpicker11 = (NumberPicker) findViewById(R.id.numberPicker11);
		numpicker11.setMaxValue(20);
		numpicker11.setMinValue(0);
		numpicker11.setWrapSelectorWheel(true);
		numpicker11.setDisplayedValues(nums);

		numpicker12 = (NumberPicker) findViewById(R.id.numberPicker12);
		numpicker12.setMaxValue(20);
		numpicker12.setMinValue(0);
		numpicker12.setWrapSelectorWheel(true);
		numpicker12.setDisplayedValues(nums);

		mButtonAdvisor = (Button) findViewById(R.id.btnAdvise);
		mButtonAdvisor.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(mContext, AdvisorActivity.class);
				startActivity(i);
			}
		});

		mButtonReset = (Button) findViewById(R.id.btnReset);
		mButtonReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("tab22");
	}

}