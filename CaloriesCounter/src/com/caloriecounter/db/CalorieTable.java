package com.caloriecounter.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CalorieTable {

	public static final String TABLE_NAME = "Calorie";

	public static final String KEY_ROWID = "id";
	public static final String KEY_CALORIE = "calorie";
	public static final String KEY_TYPE = "type";
	public static final String KEY_DATE_TIME = "date_time";

	public static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME
			+ " ("
			+ KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_CALORIE
			+ " FLOAT NOT NULL, "
			+ KEY_TYPE
			+ " INTEGER NOT NULL, "
			+ KEY_DATE_TIME + " DATETIME NOT NULL, " + ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_ENTRIES);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(CalorieTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}
}