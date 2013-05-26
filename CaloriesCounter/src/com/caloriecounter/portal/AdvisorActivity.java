package com.caloriecounter.portal;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.caloriescounter.R;

@SuppressWarnings("all")
public class AdvisorActivity extends Activity {

	LinearLayout content = null;
	List<SelectionGroup> list = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list = new LinkedList<SelectionGroup>();

		LinearLayout parentContainer = new LinearLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		parentContainer.setLayoutParams(params);
		parentContainer.setOrientation(LinearLayout.VERTICAL);

		FrameLayout title = new FrameLayout(this);
		params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		title.setLayoutParams(params);
		title.setPadding(10, 0, 10, 0);
		title.setBackgroundResource(com.example.caloriescounter.R.drawable.main_tab_frame_tabwidget_background_img2);

		TextView txtTitle = new TextView(this);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		txtTitle.setTextColor(Color.WHITE);
		txtTitle.setText("Food");
		txtTitle.setLayoutParams(params);
		txtTitle.setTextSize(18);

		title.addView(txtTitle);
		parentContainer.addView(title);

		LinearLayout buttonL = new LinearLayout(this);
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		buttonL.setLayoutParams(params);

		Button add = new Button(this);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1);
		add.setText(R.string.button_add);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addOneLine();
			}
		});

		Button save = new Button(this);
		save.setText(R.string.button_save);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1);
		save.setLayoutParams(params);

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Save To DB, next activity shows the recommend

			}
		});

		Button clear = new Button(this);
		clear.setText(R.string.button_clear);
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1);
		clear.setLayoutParams(params);
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (SelectionGroup s : list) {
					content.removeView(s.layout);
				}
				list.clear();
				addOneLine();
			}
		});

		buttonL.addView(save);
		buttonL.addView(clear);
		buttonL.addView(add);

		parentContainer.addView(buttonL);

		ScrollView contentList = new ScrollView(this);
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		contentList.setLayoutParams(params);

		content = new LinearLayout(this);
		params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.MATCH_PARENT);
		content.setLayoutParams(params);
		content.setOrientation(LinearLayout.VERTICAL);
		addOneLine();
		contentList.addView(content);
		parentContainer.addView(contentList);

		this.setContentView(parentContainer);
	}

	private void addOneLine() {
		SelectionGroup newLine = new SelectionGroup();
		this.list.add(newLine);
		this.content.addView(newLine.getWrappedLayout());
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	public class SelectionGroup {
		public Spinner food;
		public NumberPicker number;
		public TextView unit;
		public LinearLayout layout;

		private SelectionGroup() {
			food = new Spinner(AdvisorActivity.this);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(AdvisorActivity.this,
							R.array.food_list,
							android.R.layout.simple_spinner_item);
			food.setAdapter(adapter);
			LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, 3);
			param.setMargins(5, 5, 5, 5);
			food.setLayoutParams(param);

			number = new NumberPicker(AdvisorActivity.this);
			number.setMaxValue(10);
			number.setMinValue(1);
			param = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.MATCH_PARENT, 2);
			number.setLayoutParams(param);

			unit = new TextView(AdvisorActivity.this);
			unit.setTextSize(18);
			unit.setText(R.string.ui_text_unit);
			// save.setTextColor(Color.WHITE);
			param = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			unit.setLayoutParams(param);
		}

		public LinearLayout getWrappedLayout() {
			if (layout == null) {
				layout = new LinearLayout(AdvisorActivity.this);
				LayoutParams params = new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1);
				layout.setGravity(Gravity.CENTER_HORIZONTAL);
				layout.setLayoutParams(params);

				layout.addView(food);
				layout.addView(number);
				layout.addView(unit);
			}
			return layout;

		}
	}

}