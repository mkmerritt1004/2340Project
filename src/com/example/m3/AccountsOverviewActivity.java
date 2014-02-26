package com.example.m3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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

public class AccountsOverviewActivity extends Activity {
	Button newAccountButton;
	LinearLayout layout;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_overview);
        layout = (LinearLayout) findViewById(R.id.activityAccountsOverview);
        //adds all the accounts; need to update accounts
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
 
		newAccountButton = (Button) findViewById(R.id.button1);
 
		newAccountButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
			    Intent intent = new Intent(context, AccountCreationActivity.class);
                startActivity(intent);  
				
			}
		});
    
	}
	class createAccountButtonTask extends AsyncTask<String, Void, HttpResponse> {

		private Context context;
		private HttpResponse response;
		
		private createAccountButtonTask(Context context) {
		    this.context = context.getApplicationContext();
		}

	    protected HttpResponse doInBackground(String... inputs) {
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpGet httpget = new HttpGet("http://intense-garden-9893.herokuapp.com/api/users/accounts.json");

	        try {
	            // Execute HTTP Post Request
	            response = httpclient.execute(httpget);
	            
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	            System.out.println("CPE"+e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            System.out.println("IOE"+e);
	        }
	        return response;
	    }

	    protected void onPostExecute(HttpResponse response) {
			if ( response.getStatusLine().getStatusCode() == 201 ){
				Intent intent = new Intent(context, SuccessScreenActivity.class);
				startActivity(intent); 
			}
			else{
				try {
					//byte[] accountInfo =  EntityUtils.toByteArray(response.getEntity());
					//for (byte accountName: accountInfo) {
						//String accountNameStr = accountName.toString();
					//}
					String accountInfo = EntityUtils.toString(response.getEntity());
					String[] temp = new String[25];
					int startIndex = 0;
					int endIndex = 0;
					int count = 0;
					int numberOfAccounts = 0;
					while (count<accountInfo.length()){
						if (accountInfo.charAt(count) == "{".charAt(0)) {
							startIndex = count + 1;
						} else if (accountInfo.charAt(count) == "}".charAt(0)) {
							endIndex = count;
							String oneAccount = accountInfo.substring(startIndex, endIndex);
							temp[numberOfAccounts] = oneAccount;
							numberOfAccounts++;
						}
						count++;
					}
					//updateButtons()
					//get display name
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }
	}
	
}
