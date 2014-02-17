package com.example.m3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeScreenActivity extends Activity {

	Button button;
	Button regButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_welcome);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		button = (Button) findViewById(R.id.loginButton);
		regButton = (Button) findViewById(R.id.registerButton);
		
		regButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, RegistrationActivity.class);
                            startActivity(intent);   
			}
 
		});
 
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}
	
	
}
