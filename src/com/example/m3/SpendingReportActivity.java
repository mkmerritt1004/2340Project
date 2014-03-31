package com.example.m3;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SpendingReportActivity extends Activity {

	private Button backButton;
	private String startDate;
	private TextView startDateTextView;
	private String endDate;
	private TextView endDateTextView;
	private String foodSum;
	private TextView foodSumTextView;
	private String rentSum;
	private TextView rentSumTextView;
	private String entertainmentSum;
	private TextView entertainmentSumTextView;
	private String clothingSum;
	private TextView clothingSumTextView;
	private String otherSum;
	private TextView otherSumTextView;
	private String total;
	private TextView totalTextView;
	private String auth_token;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spending_report);
		loadElements();
		startDateTextView.setText(startDate);
		endDateTextView.setText(endDate);
		final Context context = this;
		try {
			HttpResponse response = new DatabaseInterface().getCategorySums(startDate, endDate, auth_token);
	        if ( response.getStatusLine().getStatusCode() == 200 ){
				try {
					String stringResponse = EntityUtils.toString(response.getEntity());
					JSONObject json = new JSONObject(stringResponse);
					foodSum = json.getString("food");
					entertainmentSum = json.getString("entertainment");
					clothingSum = json.getString("clothing");
					rentSum = json.getString("rent");
					otherSum = json.getString("other");
					total = json.getString("total");
					updateSums();
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				updateView(EntityUtils.toString(response.getEntity()));
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addListenerToButton();
	}
	
	private void loadElements() {
		Intent oldIntent = getIntent();
		auth_token = oldIntent.getStringExtra("auth_token");
		startDate = oldIntent.getStringExtra("startDate");
		startDateTextView = (TextView) findViewById(R.id.startDate);
		endDate = oldIntent.getStringExtra("endDate");
		endDateTextView = (TextView) findViewById(R.id.endDate);
		foodSumTextView = (TextView) findViewById(R.id.food_sum);
		rentSumTextView = (TextView) findViewById(R.id.rent_sum);
		clothingSumTextView = (TextView) findViewById(R.id.clothing_sum);
		otherSumTextView = (TextView) findViewById(R.id.other_sum);
		entertainmentSumTextView = (TextView) findViewById(R.id.entertainment_sum);
		totalTextView = (TextView) findViewById(R.id.total);
	}
	
	public void addListenerToButton() {
		
		final Context context = this;
		backButton = (Button) findViewById(R.id.backButton);
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, ReportDateSelectActivity.class);
                intent.putExtra("auth_token", auth_token);
				startActivity(intent);
			}
		});
	}
	
	private void updateSums() {
		foodSumTextView.setText(foodSum);
		rentSumTextView.setText(rentSum);
		clothingSumTextView.setText(clothingSum);
		entertainmentSumTextView.setText(entertainmentSum);
		otherSumTextView.setText(otherSum);
		totalTextView.setText(total);
	}
	
	private void updateView(String errors) {
		TextView errorsTextView = (TextView) findViewById(R.id.errors);
		errorsTextView.setText(errors);
	}
	
	
	
}
