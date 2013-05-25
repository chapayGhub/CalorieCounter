package com.caloriecounter.common;

import java.util.Date;

import android.database.Cursor;

/**
 * 
 * Data Object corresponding to a DB table
 * 
 */
public class CalorieEntry {
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = new Date(dateTime);
	}

	public float getCalorie() {
		return calorie;
	}

	public void setCalorie(float calorie) {
		this.calorie = calorie;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private long id;
	private float calorie;
	private int type;
	private Date dateTime;

	public static CalorieEntry buildFromCursor(Cursor c) {
		CalorieEntry result = new CalorieEntry();
		int index = 0;
		result.setId(c.getLong(index++));
		result.setCalorie(c.getFloat(index++));
		result.setType(c.getInt(index++));
		result.setDateTime(c.getLong(index++));
		return result;
	}
}
