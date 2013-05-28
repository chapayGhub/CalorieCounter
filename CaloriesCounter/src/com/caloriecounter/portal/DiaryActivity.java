package com.caloriecounter.portal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.caloriecounter.R;
import com.caloriecounter.common.ActivityEntry;
import com.caloriecounter.common.ListTextView;
import com.caloriecounter.utils.DBDateUtils;
import com.caloriecounter.utils.DataSourceBridge;

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

		activityList = mDataSourceBridge.queryActitivities(
				DBDateUtils.getDateFromString(
						DBDateUtils.getStringFromDate(new Date(System
								.currentTimeMillis()))).getTime(), null);

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

		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		float[] ary = new float[10];
		for (int i = 0; i < 9; i++) {
			if (i > 5)
				ary[i] = i;
			else
				ary[i] = 9 - i;
		}
		float[] ary1 = new float[5];
		for (int i = 0; i < 5; i++) {
			if (i <= 5)
				ary1[i] = i;
			else
				ary1[i] = 10 - i;
		}
		dataset = getDataset(ary, dataset, "In");
		dataset = getDataset(ary1, dataset, "Out");
		XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
		xyRenderer.setColor(Color.BLUE);
		xyRenderer.setPointStyle(PointStyle.SQUARE);
		renderer.addSeriesRenderer(xyRenderer);
		xyRenderer = new XYSeriesRenderer();
		xyRenderer.setColor(Color.RED);
		xyRenderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setZoomEnabled(false);
		renderer.setPanEnabled(false);
		renderer.addSeriesRenderer(xyRenderer);
		renderer.setLabelsTextSize(20);
		renderer.setChartTitle("Calories");
		renderer.setLegendTextSize(30);
		LinearLayout linechart = (LinearLayout) findViewById(R.id.barchart);
		GraphicalView lineView = ChartFactory.getLineChartView(this, dataset,
				renderer);
		linechart.addView(lineView);
	}

	private XYMultipleSeriesDataset getDataset(float[] f,
			XYMultipleSeriesDataset dataset, String name) {
		XYSeries series = new XYSeries(name);
		for (int k = 0; k < f.length; k++) {
			series.add(k, f[k]);
		}
		dataset.addSeries(series);
		return dataset;
	}

	public void OnListItemClick(ListView l, View v, int position, long id) {
		((ListAdapter) getListAdapter()).doExtern(position);
	}

	private class ListAdapter extends BaseAdapter {
		List<ListTextView> mObject = new ArrayList<ListTextView>();

		@Override
		public int getCount() {
			return mObject.size();
		}

		@Override
		public Object getItem(int position) {
			return mObject.get(position);
		}

		@Override
		public long getItemId(int position) {
			return (long) position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
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
		return durationInSeconds > 60 ? String.format(Locale.US,
				MINUTES_FORMAT, MINUTES_FORMAT) : String.format(Locale.US,
				MINUTES_FORMAT, durationInSeconds);
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