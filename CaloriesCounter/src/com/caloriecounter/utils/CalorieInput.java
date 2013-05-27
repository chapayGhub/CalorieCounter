package com.caloriecounter.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import android.util.Log;

public class CalorieInput {

	public static final int EGGS = 150;
	public static final int ICE_CREAM = 180;
	public static final int MILK = 70;
	public static final int YOGURT = 60;
	public static final int TRIFLE = 60;
	public static final int BREAD = 240;
	public static final int PASTA = 110;
	public static final int POTATOES = 70;
	public static final int RICE = 140;
	public static final int BACON = 500;
	public static final int BEEF = 280;
	public static final int CHICKEN = 200;
	public static final int APPLE = 44;
	public static final int BANANA = 65;
	public static final int STRAWBERRIES = 30;

	public static final int WALKING = 5;
	public static final int RUNNING = 13;
	public static final int SWIMMING = 11;

	static Map<String, Integer> f_c;
	static Map<String, Integer> a_c;

	static {
		f_c = new HashMap<String, Integer>();
		a_c = new HashMap<String, Integer>();
		f_c.put("EGGS", EGGS);
		f_c.put("ICE_CREAM", ICE_CREAM);
		f_c.put("MILK", MILK);
		f_c.put("YOGURT", YOGURT);
		f_c.put("BREAD", BREAD);
		f_c.put("PASTA", PASTA);
		f_c.put("POTATOES", POTATOES);
		f_c.put("RICE", RICE);
		f_c.put("BACON", BACON);
		f_c.put("BEEF", BEEF);
		f_c.put("CHICKEN", CHICKEN);
		f_c.put("APPLE", APPLE);
		f_c.put("BANANA", BANANA);
		f_c.put("STRAWBERRIES", STRAWBERRIES);
		f_c.put("TRIFLE", TRIFLE);

		a_c.put("WALKING", WALKING);
		a_c.put("RUNNING", RUNNING);
		a_c.put("SWIMMING", SWIMMING);
	}

	public static int calculateCalories(Map<String, Integer> hm) {
		int calories = 0;

		Iterator<Map.Entry<String, Integer>> itr = hm.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Integer> entry = itr.next();
			String key = entry.getKey();
			int value = entry.getValue();
			try {
				calories += f_c.get(key.toUpperCase(Locale.US)) * value;
			} catch (Exception e) {
				Log.e("calorie calculate", key + " calorie not found");
			}
		}

		return calories;
	}

	public static int getActivityMinutesByCalories(int calories, String act) {
		int result = 0;
		try {
			result = (int) (calories * 0.6f / a_c.get(act
					.toUpperCase(Locale.US)));
		} catch (Exception e) {
			Log.e("calorie calculate", act + " calorie not found");
		}
		return result;
	}

	public static float calculateCaloriesByActivity(String activity, int seconds) {
		float result = 0.0f;
		try {
			int unitCal = a_c.get(activity.toUpperCase(Locale.US));
			float time = (float) seconds * 1.0f / 60;
			result = unitCal * time;
		} catch (Exception e) {
			Log.e("calorie calculate", activity + " calorie not found");
		}
		return result;
	}
}
