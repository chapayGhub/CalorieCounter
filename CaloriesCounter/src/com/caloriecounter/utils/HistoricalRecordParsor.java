package com.caloriecounter.utils;

import java.util.List;

import android.content.Context;

import com.caloriecounter.R;
import com.caloriecounter.common.CalorieEntry;
import com.caloriecounter.common.Globals;

public class HistoricalRecordParsor {

	public static float[] getCaloriesFromPastRecord(Context c) {
		float[] result = new float[50];
		try {
			for (int i = 0; i < 50; i++) {

				float walkTime = Float.valueOf(c.getResources()
						.getString(
								R.string.class.getField("walk_" + (i + 1))
										.getInt(null)));
				float runTime = Float
						.valueOf(c.getResources().getString(
								R.string.class.getField("run_" + (i + 1))
										.getInt(null)));
				result[i] = walkTime * CalorieInput.WALKING + runTime
						* CalorieInput.RUNNING;
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static float[] getCaloriesInFromPastFood(Context c) {

		DataSourceBridge b = new DataSourceBridge(c);
		b.open();
		List<CalorieEntry> cr = b.queryCalorieRecords(null, null,
				Globals.CALORIE_TABLE_INPUT_TYPE.TYPE_IN);
		float[] result = new float[cr.size() > 50 ? 50 : cr.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = cr.get(i).getCalorie();
		}
		return result;
	}
}
