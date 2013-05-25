package com.caloriecounter.common;

import java.util.Date;

import android.database.Cursor;

/**
 * 
 * Data Object corresponding to a DB table
 * 
 */
public class WeightEntry {
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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

	private long id;
	private int weight;
	private Date dateTime;

	public static WeightEntry buildFromCursor(Cursor c) {
		WeightEntry result = new WeightEntry();
		int index = 0;
		result.setId(c.getLong(index++));
		result.setWeight(c.getInt(index++));
		result.setDateTime(c.getLong(index++));
		return result;
	}
}
