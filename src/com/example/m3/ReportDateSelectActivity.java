package com.example.m3;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

public class ReportDateSelectActivity extends Activity {
	
	private String auth_token;
	private Button submitButton;
	private Button backButton;
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	private String startDate;
	private String endDate;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_date_select);
		loadElements();		
		addListenersOnButtons();
	}
	
	@SuppressLint("NewApi")
	private void loadElements() {
		Intent oldIntent = getIntent();
        auth_token = oldIntent.getStringExtra("auth_token");
		submitButton = (Button) findViewById(R.id.submitButton);
		backButton = (Button) findViewById(R.id.backButton);
		startDatePicker = (DatePicker)findViewById(R.id.startDatePicker);
		startDatePicker.setCalendarViewShown(false);
		endDatePicker = (DatePicker)findViewById(R.id.endDatePicker);
		endDatePicker.setCalendarViewShown(false);
	}
	
	public void addListenersOnButtons() {
		final Context context = this;


		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, SpendingReportActivity.class);
				startDate = String.format("%02d/%02d/%d", startDatePicker.getMonth() + 1, 
						startDatePicker.getDayOfMonth(), startDatePicker.getYear());
				endDate = String.format("%02d/%02d/%d", endDatePicker.getMonth() + 1,
						 endDatePicker.getDayOfMonth(), endDatePicker.getYear());
                intent.putExtra("auth_token", auth_token);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
				startActivity(intent);
			}
		});
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, AccountsOverviewActivity.class);
                intent.putExtra("auth_token", auth_token);
				startActivity(intent);
			}
		});
	}
	
	
	
}
