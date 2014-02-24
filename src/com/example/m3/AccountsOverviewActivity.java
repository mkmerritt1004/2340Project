package com.example.m3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AccountsOverviewActivity extends Activity {
	Button newAccountButton;
	LinearLayout layout;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_overview);
        layout = (LinearLayout) findViewById(R.id.activityAccountsOverview);
        String[] accounts = {"Account 1", "Account 2"};
        for (String account:accounts) {
        	Button button = new Button(this);
        	button.setText(account);
            layout.addView(button);
        }

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
 
//		newAccountButton = (Button) findViewById(R.id.newAccountButton);
// 
//		newAccountButton.setOnClickListener(new OnClickListener() {
// 
//			@Override
//			public void onClick(View arg0) {
//				
//			    Intent intent = new Intent(context, MainActivity.class);
//                startActivity(intent);  
//				
//			}
// 
//		});
    
	}
	
}
