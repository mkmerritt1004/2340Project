package com.example.m3;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TransPageActivity extends Activity {
	Button deposit;
	Button withdrawal;
	Button back;
	String auth_token;
	String account_id;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trans_page);
		Intent oldIntent = getIntent();
		auth_token = oldIntent.getStringExtra("auth_token");
        account_id = oldIntent.getStringExtra("account_id");
		final Context context = this;
        new GetAccountInfoTask(context).execute();
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trans_page, menu);
		return true;
	}
	
	private void addBalanceToScreen(String balance) {
		TextView textView = (TextView) findViewById(R.id.balance_input);
        textView.setText(balance);
	}
	
	private void addNameToScreen(String name) {
		TextView textView = (TextView) findViewById(R.id.account_name_input);
        textView.setText(name);
	}
	
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		deposit = (Button) findViewById(R.id.depositButton);
		withdrawal = (Button) findViewById(R.id.withdrawalButton);
		back= (Button) findViewById(R.id.button1);
 
		deposit.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				 Intent intent = new Intent(context, DepositActivity.class);
	             intent.putExtra("auth_token", auth_token);
	             intent.putExtra("account_id", account_id);
                 startActivity(intent);  
			}
 
		});
		withdrawal.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(context, WithdrawalActivity.class);
                intent.putExtra("auth_token", auth_token);
                intent.putExtra("account_id", account_id);
				startActivity(intent);
			}
		});
		back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(context, AccountsOverviewActivity.class);
                intent.putExtra("auth_token", auth_token);
                intent.putExtra("account_id", account_id);
				startActivity(intent);
			}
		});

	}
	
	class GetAccountInfoTask extends AsyncTask<String, Void, HttpResponse> {

		private Context context;
		private HttpResponse response;
		
		private GetAccountInfoTask(Context context) {
		    this.context = context.getApplicationContext();
		}

	    protected HttpResponse doInBackground(String... inputs) {
	    	
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpGet httpget = new HttpGet("http://intense-garden-9893.herokuapp.com/api/accounts/" + account_id + ".json?authentication_token=" + auth_token);
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
					String balance = stringResponse.split(":")[3];
					String name = stringResponse.split(":")[2];
					addBalanceToScreen(balance.substring(0, balance.length() - 1));
					addNameToScreen(name.split("\"")[1]);
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
					addBalanceToScreen(EntityUtils.toString(response.getEntity()));
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
