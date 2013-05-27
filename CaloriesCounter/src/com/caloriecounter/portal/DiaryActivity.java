package com.caloriecounter.portal;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.caloriecounter.common.ActivityEntry;
import com.caloriecounter.common.ListTextView;
import com.caloriecounter.utils.DataSourceBridge;
import com.example.caloriescounter.R;

public class DiaryActivity extends ListActivity {

	public Context mContext; // context pointed to parent activity
	public DataSourceBridge mDataSourceBridge;

	// Table column index
	public int mRowIdIndex;
	public int mActivityIndex;
	public int mTimeIndex;
	public int mDurationIndex;
	public int mStepsIndex;
	public int mCaloriesIndex;

	public List<ActivityEntry> activityList;

	// Different format to display the information
	public static final String DATE_FORMAT = "H:mm:ss MMM d yyyy";
	public static final String DISTANCE_FORMAT = "#.##";
	public static final String MINUTES_FORMAT = "%d mins";
	public static final String SECONDS_FORMAT = "%d secs";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary);

		mContext = this;

		mDataSourceBridge = new DataSourceBridge(mContext);

		ListAdapter mAdapter = new ListAdapter();
		mDataSourceBridge.open();
		// TODO mok data
		for (int i = 0; i < 1; i++) {
			Random r = new Random();
			ActivityEntry a = new ActivityEntry();
			a.setActivityType(0);
			a.setSteps(r.nextInt(1000));
			a.setCalorie(100 + r.nextInt(200));
			a.setDuration(r.nextInt(60));
			mDataSourceBridge.insertActivity(a);
		}

		activityList = mDataSourceBridge.queryActitivities(null, null);

		if (!activityList.isEmpty()) {

			for (ActivityEntry mEntry : activityList) {
				String activityTypeString = parseActivityType(mEntry
						.getActivityType());
				String dateString = parseTime((Date) mEntry.getDateTime());
				Integer s = Integer.valueOf(mEntry.getSteps());
				String stepsString = s.toString() + " steps";
				String durationString = parseDuration(mEntry.getDuration());
				Float c = Float.valueOf(mEntry.getCalorie());
				String calorieString = c.toString() + " cal";

				ListTextView v = new ListTextView(mContext, activityTypeString
						+ ", " + dateString, stepsString + ", "
						+ durationString + ", " + calorieString);
				mAdapter.mObject.add(v);

			}
		}

		setListAdapter(mAdapter);
	}

	public void OnListItemClick(ListView l, View v, int position, long id) {
		((ListAdapter) getListAdapter()).doExtern(position);
	}

	// Subclass a cursor adapter for our purpose.
	// Display interpreted database row values in customized list view.

	private class ListAdapter extends BaseAdapter {
		List<ListTextView> mObject = new ArrayList<ListTextView>();

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mObject.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mObject.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return (long) position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ListTextView sv;
			if (convertView == null) {
				sv = new ListTextView(mContext, mObject.get(position).mTitle
						.getText().toString(), mObject.get(position).mSummary
						.getText().toString());

			} else {

				sv = (ListTextView) convertView;
				sv.mTitle.setText(mObject.get(position).mTitle.getText()
						.toString());
				sv.mSummary.setText(mObject.get(position).mSummary.getText()
						.toString());
			}

			sv.setExpanded(mObject.get(position).expanded == View.VISIBLE);
			return sv;
		}

		public void doExtern(int position) {
			mObject.get(position).setExpanded();
			notifyDataSetChanged();
		}

	}

	// Parser utilities to read value from database and interpret into
	// human-readable string

	// From activity type 0, 1, 2 ... to string "Running", "Walking", etc.
	private String parseActivityType(int code) {
		String activityTypes[] = getResources().getStringArray(
				R.array.activity_type_items);
		return activityTypes[code];
	}

	// From 1970 epoch time in seconds to something like "10/24/2012"
	private String parseTime(Date datetime) {

		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		return df.format(datetime);
	}

	// Convert duration in seconds to minutes.
	private String parseDuration(int durationInSeconds) {
		return durationInSeconds > 60 ? String.format(MINUTES_FORMAT,
				durationInSeconds / 60) : String.format(SECONDS_FORMAT,
				durationInSeconds);

	}

	public void onGetMoreClicked(View v) {
		// Making a "toast" informing the user changes are canceled.
		Intent intent = new Intent(DiaryActivity.this, PickDateActivity.class);
		startActivity(intent);
		Toast.makeText(getApplicationContext(),
				getString(R.string.ui_profile_toast_cancel_text),
				Toast.LENGTH_SHORT).show();

	}
}