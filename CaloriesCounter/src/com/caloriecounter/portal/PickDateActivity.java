package com.caloriecounter.portal;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.caloriecounter.common.ActivityEntry;
import com.caloriecounter.common.ListTextView;
import com.caloriecounter.utils.DataSourceBridge;
import com.example.caloriescounter.R;

public class PickDateActivity extends ListActivity {

	TextView mDisplayStartDate;
	TextView mDisplayEndDate;
	Calendar mStartDate = Calendar.getInstance();
	Calendar mEndDate = Calendar.getInstance();

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_picktime);

		mDisplayStartDate = (TextView) findViewById(R.id.startdate);
		mDisplayEndDate = (TextView) findViewById(R.id.enddate);

		updateStartDateDisplay();
		updateEndDateDisplay();

		mContext = this;

		mDataSourceBridge = new DataSourceBridge(mContext);

		ListAdapter mAdapter = new ListAdapter();

		mDataSourceBridge.open();
		// activityList = mDataSourceBridge.queryActitivities(
		// mStartDate.getTimeInMillis(), mEndDate.getTimeInMillis());
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

	public void onStartDateClicked(View v) {

		DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				mStartDate.set(Calendar.YEAR, year);
				mStartDate.set(Calendar.MONTH, monthOfYear);
				mStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateStartDateDisplay();
			}
		};

		new DatePickerDialog(PickDateActivity.this, mDateListener,
				mStartDate.get(Calendar.YEAR), mStartDate.get(Calendar.MONTH),
				mStartDate.get(Calendar.DAY_OF_MONTH)).show();

	}

	public void onEndDateClicked(View v) {

		DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				mEndDate.set(Calendar.YEAR, year);
				mEndDate.set(Calendar.MONTH, monthOfYear);
				mEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateEndDateDisplay();
			}
		};

		new DatePickerDialog(PickDateActivity.this, mDateListener,
				mEndDate.get(Calendar.YEAR), mEndDate.get(Calendar.MONTH),
				mEndDate.get(Calendar.DAY_OF_MONTH)).show();

	}

	private void updateStartDateDisplay() {
		mDisplayStartDate.setText(DateUtils.formatDateTime(this,
				mStartDate.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_SHOW_TIME));
	}

	private void updateEndDateDisplay() {
		mDisplayEndDate.setText(DateUtils.formatDateTime(this,
				mEndDate.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_SHOW_TIME));
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

}
