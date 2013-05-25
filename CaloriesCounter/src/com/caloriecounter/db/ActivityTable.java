package com.caloriecounter.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ActivityTable {

	public static final String TABLE_NAME = "Activities";

	public static final String KEY_ROWID = "id";
	public static final String KEY_ACTIVITY_TYPE = "activity_type";
	public static final String KEY_DATE_TIME = "date_time";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_Steps = "steps";

	public static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME
			+ " ("
			+ KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_ACTIVITY_TYPE
			+ " INTEGER NOT NULL, "
			+ KEY_DATE_TIME
			+ " DATETIME NOT NULL, "
			+ KEY_DURATION
			+ " INTEGER NOT NULL, "
			+ KEY_Steps
			+ " INTEGER "
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_ENTRIES);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(ActivityTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}
}