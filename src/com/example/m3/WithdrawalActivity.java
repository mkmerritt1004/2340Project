package com.example.m3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.example.m3.AccountCreationActivity.accountCreationTask;

import android.os.AsyncTask;
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

public class WithdrawalActivity extends Activity {
	private String auth_token;
	private String account_id;
	private Context context;
	private Button withdrawalButton;
	private EditText reason;
	private EditText category;
	private EditText effectiveDate;
	private EditText amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawal);
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
				category   = (EditText)findViewById(R.id.category_input);
				effectiveDate   = (EditText)findViewById(R.id.effective_date_input);
				amount   = (EditText)findViewById(R.id.amount_input);
				String reasonStr = reason.getText().toString();
				String categoryStr = category.getText().toString();
				String effectiveDateStr = effectiveDate.getText().toString();
				String amountStr = amount.getText().toString();

				new withdrawTask(context).execute(reasonStr, categoryStr, effectiveDateStr, amountStr, account_id);
				
			}
 
		});
    
	}
 class withdrawTask extends AsyncTask<String, Void, HttpResponse> {

		private Context context;
		private HttpResponse response;
		
		private withdrawTask(Context context) {
		    this.context = context.getApplicationContext();
		}

	    protected HttpResponse doInBackground(String... inputs) {
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://intense-garden-9893.herokuapp.com/api/withdrawals?authentication_token=" + auth_token);

	        try {
	            // Add your data
	            List<NameValuePair> params = new ArrayList<NameValuePair>(5);
	            params.add(new BasicNameValuePair("[withdrawal][reason]", inputs[0]));
	            params.add(new BasicNameValuePair("[withdrawal][category]", inputs[1]));
	            params.add(new BasicNameValuePair("[withdrawal][effective_date]", inputs[2]));
	            params.add(new BasicNameValuePair("[withdrawal][amount]", inputs[3]));
	            params.add(new BasicNameValuePair("[withdrawal][account_id]", inputs[4]));
	            httppost.setEntity(new UrlEncodedFormEntity(params));
	            // Execute HTTP Post Request
	            response = httpclient.execute(httppost);
	            
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
				Intent intent = new Intent(context, TransPageActivity.class);
				intent.putExtra("auth_token", auth_token);
                intent.putExtra("account_id", account_id);
				startActivity(intent); 
			}
			else{
				try {
					updateTextView(EntityUtils.toString(response.getEntity()));
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
