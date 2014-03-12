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
	
	private Button submitButton;
	private Button backButton;
	private DatePicker startDatePicker;
	private DatePicker endDatePicker;
	private Date startDate;
	private Date endDate;
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_date_select);
		addListenerOnButton();
		this.startDate = new Date(startDatePicker.getYear(),
				startDatePicker.getMonth(), startDatePicker.getDayOfMonth());
		this.endDate = new Date(endDatePicker.getYear(), 
				endDatePicker.getMonth(), endDatePicker.getDayOfMonth());
	}
	
	@SuppressLint("NewApi")
	public void addListenerOnButton() {
		final Context context = this;
		submitButton = (Button) findViewById(R.id.submitButton);
		backButton = (Button) findViewById(R.id.backButton);
		startDatePicker = (DatePicker)findViewById(R.id.startDatePicker);
		endDatePicker = (DatePicker)findViewById(R.id.endDatePicker);
		startDatePicker.setCalendarViewShown(false);
		endDatePicker.setCalendarViewShown(false);
		submitButton.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				startDatePicker = (DatePicker)findViewById(R.id.startDatePicker);
				endDatePicker = (DatePicker)findViewById(R.id.endDatePicker);
				startDate = new Date(startDatePicker.getYear(),
						startDatePicker.getMonth(), startDatePicker.getDayOfMonth());
				endDate = new Date(endDatePicker.getYear(), 
						endDatePicker.getMonth(), endDatePicker.getDayOfMonth());
				Intent intent = new Intent(context, SpendingReportActivity.class);
				startActivity(intent);
			}
		});
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, AccountsOverviewActivity.class);
				startActivity(intent);
			}
		});
	}
	
	
	
}