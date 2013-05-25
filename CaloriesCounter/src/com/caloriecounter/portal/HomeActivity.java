package com.caloriecounter.portal;

import com.example.caloriescounter.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
	private Button mButtonStart;
	private Button mButtonEnd;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		System.out.println("tab1");

		mContext = this;

		mButtonStart = (Button) findViewById(R.id.btnStart);

		mButtonStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// new
				// AlertDialog.Builder(mContext).setTitle("ActivityType").setItems(
				// new String[] { "Auto", "Walking" }, null).setNegativeButton(
				// "Start", null).show();
				new AlertDialog.Builder(mContext)
						.setTitle("ActivityType")
						.setMultiChoiceItems(
								new String[] { "Auto", "Walking", "Running" },
								null, null).setPositiveButton("Confirm", null)
						.setNegativeButton("Cancel", null).show();
				AlertDialog.Builder builder = new Builder(mContext);
				builder.setTitle("ActivityType");
				builder.setMultiChoiceItems(new String[] { "Auto", "Walking",
						"Running", "Jumping", "Standing" }, null, null);
				builder.setPositiveButton("Confirm",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});

				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});

			}
		});

		mButtonEnd = (Button) findViewById(R.id.btnEnd);
		mButtonEnd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(mContext);
				builder.setTitle("Your Choice");
				builder.setMessage("Save to diary?");
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub

							}
						});
				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				builder.create().show();
			}
		});

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("tab11");
	}
}