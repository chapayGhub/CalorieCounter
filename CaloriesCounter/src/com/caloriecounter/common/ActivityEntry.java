package com.caloriecounter.common;

import java.util.Date;

import android.database.Cursor;

/**
 * 
 * Data Object corresponding to a DB table
 * 
 */
public class ActivityEntry {
	private long id;
	private int activityType;
	private Date dateTime;
	private int duration;
	private int steps;

	private float calorie;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getActivityType() {
		return activityType;
	}

	public float getCalorie() {
		return calorie;
	}

	public void setCalorie(float calorie) {
		this.calorie = calorie;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public static ActivityEntry buildFromCursor(Cursor c) {
		ActivityEntry result = new ActivityEntry();
		int index = 0;
		result.setId(c.getLong(index++));
		result.setActivityType(c.getInt(index++));
		result.setDateTime(c.getLong(index++));
		result.setDuration(c.getInt(index++));
		result.setSteps(c.getInt(index++));
		result.setCalorie(c.getFloat(index++));
		return result;
	}
}
