package com.caloriecounter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "CCDB";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		ActivityTable.onCreate(db);
		WeightTable.onCreate(db);
		CalorieTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		ActivityTable.onUpgrade(db, oldVersion, newVersion);
		WeightTable.onUpgrade(db, oldVersion, newVersion);
		CalorieTable.onUpgrade(db, oldVersion, newVersion);
	}

}