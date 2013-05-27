package com.caloriecounter.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
@SuppressWarnings("all")
public class ListTextView extends LinearLayout {
	public TextView mTitle;
	public TextView mSummary;
	public int expanded;

	public ListTextView(Context context, String title, String summary) {
		super(context);
		this.setOrientation(VERTICAL);
		mTitle = new TextView(context);
		mTitle.setText(title);
		mTitle.setTextSize(18);
		addView(mTitle, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		mSummary = new TextView(context);
		mSummary.setText(summary);
		mSummary.setTextSize(14);
		addView(mSummary, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		mSummary.setVisibility(GONE);

	}

	public void setExpanded() {
		expanded = expanded == GONE ? VISIBLE : GONE;
		mSummary.setVisibility(expanded);
	}

	public void setExpanded(boolean ifExtern) {
		expanded = ifExtern ? VISIBLE : GONE;
		mSummary.setVisibility(expanded);
	}

}
