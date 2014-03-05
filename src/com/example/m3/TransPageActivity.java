package com.example.m3;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TransPageActivity extends Activity {
	Button deposit;
	Button withdrawal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trans_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trans_page, menu);
		return true;
	}
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		deposit = (Button) findViewById(R.id.depositButton);
		withdrawal = (Button) findViewById(R.id.withdrawalButton);
 
		deposit.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				 Intent intent = new Intent(context, DepositActivity.class);
                 startActivity(intent);  
			}
 
		});
		withdrawal.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(context, WithdrawalActivity.class);
				startActivity(intent);
			}
		});

	}
}
