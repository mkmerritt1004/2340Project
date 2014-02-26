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

import com.example.m3.MainActivity.CheckLoginTask;

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
	String auth_token;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Intent oldIntent = getIntent();
        auth_token = oldIntent.getStringExtra("auth_token");
        setContentView(R.layout.activity_accounts_overview);
        layout = (LinearLayout) findViewById(R.id.activityAccountsOverview);
		final Context context = this;
        new CreateAccountsButtonTask(context).execute();
        addListenerOnCreateButton();
    }

	private void createButton(String accountName) {
    	Button button = new Button(this);
    	button.setText(accountName);
        layout.addView(button);
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
                intent.putExtra("auth_token", auth_token);
                startActivity(intent);  
			}
		});
    
	}
	
	class CreateAccountsButtonTask extends AsyncTask<String, Void, HttpResponse> {

		private Context context;
		private HttpResponse response;
		
		private CreateAccountsButtonTask(Context context) {
		    this.context = context.getApplicationContext();
		}

	    protected HttpResponse doInBackground(String... inputs) {
	    	
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpGet httpget = new HttpGet("http://intense-garden-9893.herokuapp.com/api/users/accounts.json?authentication_token=" + auth_token);

	        try {
	            // Execute HTTP Get Request
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
			if ( response.getStatusLine().getStatusCode() == 200 ){
				try {
					String stringResponse = EntityUtils.toString(response.getEntity());
					if (stringResponse.length() > 10) {
						String arrayStringResponse = stringResponse.substring(1, stringResponse.length() - 1);
						String[] accounts = arrayStringResponse.split("\\}");
						ArrayList<String> accountNameArray = new ArrayList<String>();
						for (String account : accounts) {
							String[] attr = account.substring(1).split(",");
							accountNameArray.add(attr[2]);
						}
						for (String name : accountNameArray) {
							createButton(name.substring(16, name.length() - 1));
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
					createButton(EntityUtils.toString(response.getEntity()));
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
