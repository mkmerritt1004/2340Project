package com.example.m3;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SuccessScreenActivity extends Activity{
		Button viewAccountsButton;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_success);
	        addListenerOnCreateButton();
	    }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	    
	    public void addListenerOnCreateButton() {
			 
			final Context context = this;
	 
			viewAccountsButton = (Button) findViewById(R.id.viewAccountsButton);
	 
			viewAccountsButton.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
					
				    Intent intent = new Intent(context, AccountsOverviewActivity.class);
	                startActivity(intent);  
					
				}
	 
			});
	    
		}

}
