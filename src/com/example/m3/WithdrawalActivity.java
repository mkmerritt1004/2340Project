package com.example.m3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class WithdrawalActivity extends Activity {
	private String auth_token;
	private String account_id;
	private Context context;
	private Button withdrawalButton;
	private EditText reason;
	private Spinner category;
	private DatePicker effectiveDate;
	private EditText amount;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawal);
		effectiveDate   = (DatePicker)findViewById(R.id.startDatePicker);
		effectiveDate.setCalendarViewShown(false);
		Intent oldIntent = getIntent();
		auth_token = oldIntent.getStringExtra("auth_token");
        account_id = oldIntent.getStringExtra("account_id");
        context = this;
        addListenerOnCreateButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.withdrawal, menu);
		return true;
	}
	
	public void updateTextView(String newStr) {
        TextView textView = (TextView) findViewById(R.id.errors);
        textView.setText(newStr);
    }
	
	public void addListenerOnCreateButton() {
		 
		final Context context = this;
 
		withdrawalButton = (Button) findViewById(R.id.withdrawalButton);
 
		withdrawalButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				reason   = (EditText)findViewById(R.id.reason_input);
				category   = (Spinner)findViewById(R.id.spinner1);
				amount   = (EditText)findViewById(R.id.amount_input);
				String reasonStr = reason.getText().toString();
				String categoryStr = category.getSelectedItem().toString();
				int day = effectiveDate.getDayOfMonth();
				int month=effectiveDate.getMonth()+1;
				int year=effectiveDate.getYear();
				String date= day+"/"+month+"/"+year;
				String amountStr = amount.getText().toString();

				try {
					HttpResponse response = new DatabaseInterface().withdraw(reasonStr, categoryStr, date, amountStr, account_id, auth_token);
					if ( response.getStatusLine().getStatusCode() == 201 ){
						Intent intent = new Intent(context, TransPageActivity.class);
						intent.putExtra("auth_token", auth_token);
			            intent.putExtra("account_id", account_id);
						startActivity(intent); 
					}
					else{
						updateTextView(EntityUtils.toString(response.getEntity()));
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
				
				
			}
 
		});
    
	}

}
