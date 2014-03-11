package com.example.m3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SpendingReportActivity extends Activity {

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
	}
	
	private void calculateTotal() {
		this.total = foodCost + rentCost + entertainmentCost + clothingCost + otherCost;
	}
	
}
