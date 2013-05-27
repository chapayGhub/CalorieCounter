package com.caloriecounter.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.caloriecounter.common.ActivityEntry;
import com.caloriecounter.common.CalorieEntry;
import com.caloriecounter.common.WeightEntry;
import com.caloriecounter.db.ActivityTable;
import com.caloriecounter.db.CalorieTable;
import com.caloriecounter.db.DBHelper;
import com.caloriecounter.db.WeightTable;

/**
 * DataSourceBridge
 * 
 * all operations accessing the DB should go with this Class
 * 
 */
public class DataSourceBridge {

	private ActivityEntry activityRecord;
	private WeightEntry weightRecord;

	private DBHelper mHelper;
	private SQLiteDatabase database;

	private final String TAG = "db.access";

	public DataSourceBridge(Context context) {
		activityRecord = new ActivityEntry();
		weightRecord = new WeightEntry();
		mHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		database = mHelper.getWritableDatabase();
	}

	public void close() {
		mHelper.close();
	}

	public long insertActivity(ActivityEntry value) {
		ContentValues values = new ContentValues();
		values.put(ActivityTable.KEY_ACTIVITY_TYPE, value.getActivityType());
		values.put(ActivityTable.KEY_DATE_TIME, System.currentTimeMillis());
		values.put(ActivityTable.KEY_DURATION, value.getDuration());
		values.put(ActivityTable.KEY_Steps, value.getSteps());
		values.put(ActivityTable.KEY_CALORIE, value.getCalorie());
		long insertId = database.insert(ActivityTable.TABLE_NAME, null, values);
		Log.v(TAG, "ACTIVITY entry stored: id = " + insertId);
		return insertId;
	}

	public long insertWeightRecord(WeightEntry value) {
		ContentValues values = new ContentValues();
		values.put(WeightTable.KEY_WEIGHT, value.getWeight());
		values.put(WeightTable.KEY_DATE_TIME, System.currentTimeMillis());
		long insertId = database.insert(WeightTable.TABLE_NAME, null, values);
		Log.v(TAG, "WEIGHT entry stored: id = " + insertId);
		return insertId;
	}

	public long insertCalorie(CalorieEntry value) {
		ContentValues values = new ContentValues();
		values.put(CalorieTable.KEY_CALORIE, value.getCalorie());
		values.put(CalorieTable.KEY_DATE_TIME, System.currentTimeMillis());
		values.put(CalorieTable.KEY_TYPE, value.getType());

		long insertId = database.insert(CalorieTable.TABLE_NAME, null, values);
		Log.v(TAG, "CALORIE entry stored: id = " + insertId);
		return insertId;
	}

	/**
	 * if params are null then query all the records
	 * 
	 * params can be get from DBDateUtils
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ActivityEntry> queryActitivities(Long startTime, Long endTime) {
		List<ActivityEntry> result = new ArrayList<ActivityEntry>();
		StringBuffer selection = new StringBuffer();
		if (startTime != null) {
			selection.append(" " + ActivityTable.KEY_DATE_TIME + ">"
					+ startTime + "");
		}

		if (endTime != null) {
			if (selection.length() > 0) {
				selection.append(" and ");
			}
			selection.append(" " + ActivityTable.KEY_DATE_TIME + "<" + endTime
					+ "");
		}

		Cursor cursor = database.query(ActivityTable.TABLE_NAME, null,
				selection.toString(), null, null, null, ActivityTable.KEY_ROWID
						+ " desc");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ActivityEntry activity = ActivityEntry.buildFromCursor(cursor);
			result.add(activity);
			cursor.moveToNext();
		}
		cursor.close();
		return result;
	}

	/**
	 * if params are null then query all the records
	 * 
	 * params can be get from DBDateUtils
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<WeightEntry> queryWeightRecords(Long startTime, Long endTime) {
		List<WeightEntry> result = new ArrayList<WeightEntry>();
		StringBuffer selection = new StringBuffer();
		if (startTime != null) {
			selection.append(" " + ActivityTable.KEY_DATE_TIME + ">"
					+ startTime + "");
		}

		if (endTime != null) {
			if (selection.length() > 0) {
				selection.append(" and ");
			}
			selection.append(" " + ActivityTable.KEY_DATE_TIME + "<" + endTime
					+ "");
		}

		Cursor cursor = database.query(WeightTable.TABLE_NAME, null,
				selection.toString(), null, null, null, WeightTable.KEY_ROWID
						+ " desc");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			WeightEntry activity = WeightEntry.buildFromCursor(cursor);
			result.add(activity);
			cursor.moveToNext();
		}
		cursor.close();
		return result;
	}

	/**
	 * if params are null then query all the records
	 * 
	 * params can be get from DBDateUtils
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<CalorieEntry> queryCalorieRecords(Long startTime, Long endTime,
			Integer type) {
		List<CalorieEntry> result = new ArrayList<CalorieEntry>();
		StringBuffer selection = new StringBuffer();
		if (startTime != null) {
			selection.append(" " + ActivityTable.KEY_DATE_TIME + ">"
					+ startTime + "");
		}

		if (endTime != null) {
			if (selection.length() > 0) {
				selection.append(" and ");
			}
			selection.append(" " + ActivityTable.KEY_DATE_TIME + "<" + endTime
					+ "");
		}
		if (type != null) {
			selection.append(" " + CalorieTable.KEY_TYPE + "=" + type);
		}

		Cursor cursor = database.query(CalorieTable.TABLE_NAME, null,
				selection.toString(), null, null, null, CalorieTable.KEY_ROWID
						+ " desc");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CalorieEntry activity = CalorieEntry.buildFromCursor(cursor);
			result.add(activity);
			cursor.moveToNext();
		}
		cursor.close();
		return result;
	}

	/******* standard getters and setters *******/
	public ActivityEntry getActivityRecord() {
		return activityRecord;
	}

	public void setActivityRecord(ActivityEntry activityRecord) {
		this.activityRecord = activityRecord;
	}

	public WeightEntry getWeightRecord() {
		return weightRecord;
	}

	public void setWeightRecord(WeightEntry weightRecord) {
		this.weightRecord = weightRecord;
	}

}
