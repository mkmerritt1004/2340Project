package com.example.m3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SpendingReportActivity extends Activity {

	private Button backButton2;
	private double foodCost;
	private double rentCost;
	private double entertainmentCost;
	private double clothingCost;
	private double otherCost;
	private double total;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_date_select);
		this.calculateTotal();
		addListenerToButton();
	}
	
	private void calculateTotal() {
		this.total = foodCost + rentCost + entertainmentCost + clothingCost + otherCost;
	}
	
	public void addListenerToButton() {
		
		final Context context = this;
		backButton2 = (Button) findViewById(R.id.backButton2);
		
		backButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, ReportDateSelectActivity.class);
				startActivity(intent);
			}
		});
	}
	
}
