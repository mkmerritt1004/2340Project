package com.example.m3;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DepositActivity extends Activity {
	
	/**
	 * Authentication token string instance variable.
	 */
    private String auth_token;
	private String account_id;
	private Button depositButton;
	private EditText source;
	private EditText effectiveDate;
	private EditText amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deposit);
		Intent oldIntent = getIntent();
		auth_token = oldIntent.getStringExtra("auth_token");
        account_id = oldIntent.getStringExtra("account_id");
        addListenerOnCreateButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deposit, menu);
		return true;
	}
	
	public void updateTextView(String newStr) {
        TextView textView = (TextView) findViewById(R.id.errors);
        textView.setText(newStr);
    }
	
	public void addListenerOnCreateButton() {
		 
		final Context context = this;
 
		depositButton = (Button) findViewById(R.id.depositButton);
 
		depositButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				source   = (EditText)findViewById(R.id.source_input);
				effectiveDate   = (EditText)findViewById(R.id.effective_date_input);
				amount   = (EditText)findViewById(R.id.amount_input);
				String sourceStr = source.getText().toString();
				String effectiveDateStr = effectiveDate.getText().toString();
				String amountStr = amount.getText().toString();
				try {
					HttpResponse response = new DatabaseInterface().deposit(sourceStr, effectiveDateStr, amountStr, account_id, auth_token);
					if (response.getStatusLine().getStatusCode() == 201 ){
						Intent intent = new Intent(context, TransPageActivity.class);
						intent.putExtra("auth_token", auth_token);
			            intent.putExtra("account_id", account_id);
						startActivity(intent); 
					} else {
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
