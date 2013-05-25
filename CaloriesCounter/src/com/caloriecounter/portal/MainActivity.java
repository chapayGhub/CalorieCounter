package com.caloriecounter.portal;

import com.example.caloriescounter.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
	private TabHost tabHost = null;
	private LayoutInflater mInflater = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mInflater = LayoutInflater.from(this);

		tabHost = (TabHost) findViewById(R.id.mytabhost);
		tabHost.setup(this.getLocalActivityManager());

		Intent intent = new Intent(this, HomeActivity.class);
		View tab1Spec = mInflater.inflate(R.layout.tab1_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(tab1Spec)
				.setContent(intent));

		intent = new Intent(this, AdvisorActivity.class);
		View tab2Spec = mInflater.inflate(R.layout.tab2_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(tab2Spec)
				.setContent(intent));

		intent = new Intent(this, DiaryActivity.class);
		View tab3Spec = mInflater.inflate(R.layout.tab3_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(tab3Spec)
				.setContent(intent));

		intent = new Intent(this, ProfileActivity.class);
		View tab4Spec = mInflater.inflate(R.layout.tab4_spec, null);
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(tab4Spec)
				.setContent(intent));

	}

	class MyOnTabChangeListener implements OnTabChangeListener {
		public void onTabChanged(String tabId) {

		}
	}
}