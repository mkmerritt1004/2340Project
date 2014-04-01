package com.example.m3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Activity class representing and account's overview.
 * 
 * @author Tripp
 * @version 1.0
 */
public class AccountsOverviewActivity extends Activity {
    
	/**
     * newAccountButton.
     */
    Button newAccountButton;
	Button spendingReportButton;
	LinearLayout layout;
	String auth_token;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Intent oldIntent = getIntent();
        auth_token = oldIntent.getStringExtra("auth_token");
        setContentView(R.layout.activity_accounts_overview);
        layout = (LinearLayout) findViewById(R.id.activityAccountsOverview);
		final Context context = this;
        HttpResponse response;
		try {
			response = new DatabaseInterface().getAccounts(auth_token);
			if ( response.getStatusLine().getStatusCode() == 200 ){
				try {
					String stringResponse = EntityUtils.toString(response.getEntity());
					if (stringResponse.length() > 10) {
						String arrayStringResponse = stringResponse.substring(1, stringResponse.length() - 1);
						String[] accounts = arrayStringResponse.split("\\}");
						ArrayList<String> accountNameArray = new ArrayList<String>();
						ArrayList<String> accountIdArray = new ArrayList<String>();
						for (String account : accounts) {
							String[] attr = account.substring(1).split(",");
							accountNameArray.add(attr[2]);
							accountIdArray.add(attr[0]);
						}
						for (int i = 0; i < accountNameArray.size(); i++) {
							String id = accountIdArray.get(i).split(":")[1];
							createButton(accountNameArray.get(i).substring(16, 
									accountNameArray.get(i).length() - 1), id );
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				try {
					createButton(EntityUtils.toString(response.getEntity()), "0");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        addListenerOnCreateButton();
    }

	private void createButton(String accountName, final String accountId) {
    	Button button = new Button(this);
    	button.setText(accountName);
        layout.addView(button);
        final Context context = this;
        button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, TransPageActivity.class);
                intent.putExtra("auth_token", auth_token);
                intent.putExtra("account_id", accountId);
                startActivity(intent);
			}
 
		});
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	public void addListenerOnCreateButton() {
		 
		final Context context = this;
 
		newAccountButton = (Button) findViewById(R.id.depositButton);
		spendingReportButton = (Button) findViewById(R.id.backButton2);
 
		newAccountButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, AccountCreationActivity.class);
                intent.putExtra("auth_token", auth_token);
                startActivity(intent);  
			}
		});
		
		spendingReportButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent intent = new Intent(context, ReportDateSelectActivity.class);
	             intent.putExtra("auth_token", auth_token);
	             startActivity(intent); 
			}
		});
	}
	
	
	
}
