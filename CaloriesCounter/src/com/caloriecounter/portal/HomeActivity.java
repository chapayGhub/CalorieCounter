package com.caloriecounter.portal;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.caloriecounter.common.ActivityEntry;
import com.caloriecounter.common.CalorieEntry;
import com.caloriecounter.common.Globals;
import com.caloriecounter.utils.CalorieInput;
import com.caloriecounter.utils.DataSourceBridge;
import com.example.caloriescounter.R;

public class HomeActivity extends Activity {

	public static final String S_TAG = "G-SENSOR";
	public static final String S_NOTIFICATION_TITLE = "G-SENSOR";
	public static final String S_NOTIFICATION_CONTENT = "recording your steps now";
	public static final double RUNNING_WALKING_THRESHOLD = 0.0015;

	public DataSourceBridge mDataSourceBridge;

	private TextView mTextview1;
	private TextView mTextview2;
	private Button mButtonStart;
	private Button mButtonEnd;
	private ImageView mImageView;

	private TextView mTextview3;

	private Context mContext;

	private SensorManager sensorManager;
	private SensorEventListener sel;
	private NotificationManager mNotificationManager;
	Sensor accelerometerSensor;

	int onStartClickedFlag = 0;
	String activity_type = "Walking";

	private volatile int duration = 0;
	// private int durationPause = 0;
	// private int dura = 0;
	float[] s;
	float[] s1;

	GregorianCalendar timeStarted;
	int step = 0;
	int flag = 0;
	int i1 = 0;

	int pre_step = 0;
	int pre_duration = 0;
	int duration_float = 0;

	boolean stoped = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mContext = this;

		mImageView = (ImageView) findViewById(R.id.imageProfile);
		mImageView.setImageResource(R.drawable.icon);

		mTextview1 = (TextView) findViewById(R.id.s_text1);
		mTextview2 = (TextView) findViewById(R.id.s_text2);
		mButtonStart = (Button) findViewById(R.id.btnStart);
		mButtonEnd = (Button) findViewById(R.id.btnEnd);

		mTextview3 = (TextView) findViewById(R.id.s_text3);

		reset();

		s = new float[200];
		s1 = new float[200];
		for (int i = 0; i < 200; i++) {
			s[i] = 0;
			s1[i] = 0;
		}

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0) {
			mTextview1.setText("wrong!");
		}

		/*
		 * if (savedInstanceState != null) { step =
		 * savedInstanceState.getInt("STEPSAVED"); duration +=
		 * savedInstanceState.getInt("TIMESAVED"); }
		 */

		sel = new SensorEventListener() {

			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onSensorChanged(SensorEvent event) {

				float y = event.values[SensorManager.DATA_Y];

				for (int i = 1; i < 200; i++) {
					s1[i - 1] = s[i];
				}
				s1[199] = y;

				if (s1[0] != 0) {
					flag = 1;
					// mTextview1.setText("s1[0]!=0");
				}
				if (flag == 1) {
					for (int i = 1; i < 30; i++) {
						if (s1[100] > s1[100 - i]) {
							if (s1[100] > 10)
								i1++;
						}
					}
					for (int i = 1; i < 30; i++) {
						if (s1[100] > s1[100 + i]) {
							if (s1[100] > 10)
								i1++;
						}
					}

					if (i1 == 58) {
						step++;
						mTextview1.setText("Step: " + String.valueOf(step));
						if ((step - pre_step) >= 2) {
							float res = (float) (step - pre_step)
									/ (float) (duration_float - pre_duration);
							if (res >= RUNNING_WALKING_THRESHOLD) {
								activity_type = "Running";
							} else {
								activity_type = "Walking";
							}
							mTextview3.setText(activity_type);
							pre_step = step;
							pre_duration = ((int) (System.currentTimeMillis() - timeStarted
									.getTimeInMillis()));

						}
					}
				}
				if (!stoped) {
					duration = ((int) ((System.currentTimeMillis() - timeStarted
							.getTimeInMillis()) / 1000));
					duration_float = ((int) (System.currentTimeMillis() - timeStarted
							.getTimeInMillis()));
					mTextview2.setText("Time: " + timeFormat(duration));
				}

				i1 = 0;
				flag = 0;
				for (int i = 0; i < 200; i++) {
					s[i] = s1[i];
				}
			}
		};

		accelerometerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		OnClickListener mButtonStartListener = new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				sensorManager.registerListener(sel, accelerometerSensor,
						SensorManager.SENSOR_DELAY_FASTEST);
				timeStarted = new GregorianCalendar();
				onStartClickedFlag = 1;
				mButtonStart.setEnabled(false);
				stoped = false;
				mTextview3.setText("Walking");
			}
		};

		mButtonStart.setOnClickListener(mButtonStartListener);

		mButtonEnd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (onStartClickedFlag == 1) {
					// ---------------------
					if (flag == 0) {
						sensorManager.unregisterListener(sel);
						duration = ((int) ((System.currentTimeMillis() - timeStarted
								.getTimeInMillis()) / 1000));
						duration_float = ((int) (System.currentTimeMillis() - timeStarted
								.getTimeInMillis()));
						onStartClickedFlag = 0;
						mTextview3.setText("Ended!");
						stoped = true;
					}

					reset();
					AlertDialog.Builder builder = new Builder(mContext);
					builder.setTitle("Your Choice");
					builder.setMessage("Save to diary?");
					builder.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {

									float calorie = 0;
									if (activity_type.equals("Walking")) {
										calorie = CalorieInput.WALKING
												* ((float) duration / (float) 60);
									} else {
										calorie = CalorieInput.RUNNING
												* ((float) duration / (float) 60);
									}

									mDataSourceBridge = new DataSourceBridge(
											mContext);
									mDataSourceBridge.open();

									ActivityEntry a = new ActivityEntry();
									if (activity_type.equals("Walking")) {
										a.setActivityType(0);
									} else {
										a.setActivityType(1);
									}
									// a.setActivityType(0);
									a.setSteps(step);
									a.setCalorie(calorie);
									a.setDuration(duration);
									mDataSourceBridge.insertActivity(a);

									CalorieEntry c = new CalorieEntry();
									c.setType(Globals.CALORIE_TABLE_INPUT_TYPE.TYPE_OUT);
									c.setCalorie(calorie);
									mDataSourceBridge.insertCalorie(c);

									mDataSourceBridge.close();
									jumpToShow();

								}
							});
					builder.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									jumpToShow();
								}
							});
					step = 0;
					builder.create().show();
					mButtonStart.setEnabled(true);

				}
			}
		});

		Intent i = new Intent(this.getApplication(), MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

		Notification notification = new Notification.Builder(this)
				.setContentTitle(S_NOTIFICATION_TITLE)
				.setContentText(S_NOTIFICATION_CONTENT)
				.setSmallIcon(R.drawable.icon).setContentIntent(pi).build();
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification.flags = notification.flags
				| Notification.FLAG_ONGOING_EVENT;

		mNotificationManager.notify(0, notification);

	}

	public void jumpToShow() {
		Intent i = new Intent(mContext, HomeInnerActivity.class);
		float calorie = 0;
		if (activity_type.equals("Walking")) {
			calorie = CalorieInput.WALKING * ((float) duration / (float) 60);
		} else {
			calorie = CalorieInput.RUNNING * ((float) duration / (float) 60);
		}

		i.putExtra("calories", calorie);
		startActivity(i);
	}

	private void reset() {
		step = 0;
		flag = 0;
		i1 = 0;
		pre_step = 0;
		pre_duration = 0;
		duration_float = 0;
		// stoped = false;
		duration = 0;
		mTextview1.setText("Step: " + String.valueOf(step));
		mTextview2.setText("Time: " + timeFormat(duration));
		mTextview3.setText("Not start yet!");
	}

	@Override
	/*
	 * protected void onRestart() { super.onRestart();
	 * System.out.println("tab11"); }
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	/*
	 * protected void onSaveInstanceState(Bundle outState) {
	 * super.onSaveInstanceState(outState); // Save the tab index before the
	 * activity goes into background. // Referred by string key TAB_INDEX_KEY
	 * Toast.makeText(this, "savedInstanceIn", Toast.LENGTH_SHORT).show(); int
	 * stepSaved = Integer.valueOf(mTextview1.getText().toString());
	 * outState.putInt("STEPSAVED", stepSaved); int timeSaved =
	 * Integer.valueOf(mTextview2.getText().toString());
	 * outState.putInt("TIMESAVED", timeSaved); }
	 */

	protected void onResume() {
		super.onResume();
		// Sensor accelerometerSensor =
		// sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// sensorManager.registerListener(sel,
		// accelerometerSensor,SensorManager.SENSOR_DELAY_FASTEST);
		// timeStarted = new GregorianCalendar();

	}

	protected void onPause() {
		// sensorManager.unregisterListener(sel);
		// mytextview1.setText("step:"+step);
		// mTextview1.setText(String.valueOf(step));
		// durationPause += ((int) ((System.currentTimeMillis() - timeStarted
		// .getTimeInMillis()) / 1000));

		// --------------------
		// dura = durationPause;
		// duration = ((int) ((System.currentTimeMillis() - timeStarted
		// .getTimeInMillis()) / 1000));
		// dura = duration;
		// mTextview2.setText(String.valueOf(duration));

		// mTextview2.setText(String.valueOf(durationPause));
		super.onPause();
	}

	public void onDestroy() {
		((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
				.cancelAll();
		// finish();
		super.onDestroy();
	}

	public void onBackPressed() {

		((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
				.cancelAll();
		super.onBackPressed();
	}

	public int getStep() {
		return step;
	}

	public int getDuration() {
		// dura = duration;
		// mTextview2.setText(String.valueOf(duration));
		return duration;
	}

	public String timeFormat(int mins) {
		String res = "";
		if (mins >= 60) {
			res = String.valueOf(mins / 60) + " mins "
					+ String.valueOf(mins % 60) + " sec";
		} else {
			res = String.valueOf(mins) + " sec";
		}
		return res;
	}

}
