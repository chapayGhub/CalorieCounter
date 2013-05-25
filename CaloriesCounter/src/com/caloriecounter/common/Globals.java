package com.caloriecounter.common;

/**
 * 
 * To store constants, Be sure to use interface to separate business logics
 * 
 */
public class Globals {

	public interface TABLE_NAMES {
		public final String TABLE_ACTIVITY = "Activities";
		public final String TABLE_WEIGHT = "Weight";
	}

	public interface CALORIE_TABLE_INPUT_TYPE {
		public final int TYPE_IN = 0;
		public final int TYPE_OUT = 1;
	}
	
}
