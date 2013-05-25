package com.caloriecounter.portal;

import java.util.ArrayList;
import java.util.List;

import com.example.caloriescounter.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AdvisorInnerActivity extends Activity {

	private Spinner sp1;
	private ArrayAdapter<String> adapter;
	private List<String> type = new ArrayList<String>();

	private Button mButtonKnow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advisor);
		type.add("Walking");
		type.add("Running");
		type.add("standing");
		type.add("Sleeping");
		type.add("Jumping");

		sp1 = (Spinner) findViewById(R.id.sp1);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, type);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter);
		sp1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				arg0.setVisibility(View.VISIBLE);
			}
		});

		mButtonKnow = (Button) findViewById(R.id.btnKnow);
		mButtonKnow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

}
