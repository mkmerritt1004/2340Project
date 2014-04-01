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

/**
 * Activity class that represents account creation.
 * 
 * @author Tripp Roberts
 * @version 1.0
 *
 */
public class AccountCreationActivity extends Activity {
    
	/**
    * The "Register" button.
    */
    Button registerButton;
    
	EditText name;
	EditText accountName;
	EditText balance;
	EditText interestRate;
	String auth_token;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent oldIntent = getIntent();
		auth_token = oldIntent.getStringExtra("auth_token");
		setContentView(R.layout.activity_account_creation);
        addListenerOnCreateButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_creation, menu);
		return true;
	}
	
	public void updateTextView(String newStr) {
        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText(newStr);
    }
	
	 public void addListenerOnCreateButton() {
		 
			final Context context = this;
	 
			registerButton = (Button) findViewById(R.id.depositButton);
	 
			registerButton.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
					name   = (EditText)findViewById(R.id.editText1);
					accountName   = (EditText)findViewById(R.id.editText2);
					balance   = (EditText)findViewById(R.id.editText3);
					interestRate   = (EditText)findViewById(R.id.editText4);
					String nameStr = name.getText().toString();
					String accountNameStr = accountName.getText().toString();
					String balanceDouble = balance.getText().toString();
					String interestRateDouble = interestRate.getText().toString();

					new accountCreationTask(context).execute(nameStr, accountNameStr, balanceDouble, interestRateDouble);
					
				}
	 
			});
	    
		}
	 class accountCreationTask extends AsyncTask<String, Void, HttpResponse> {

			private Context context;
			private HttpResponse response;
			
			private accountCreationTask(Context context) {
			    this.context = context.getApplicationContext();
			}

		    protected HttpResponse doInBackground(String... inputs) {
		    	HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://intense-garden-9893.herokuapp.com/api/accounts?authentication_token=" + auth_token);

		        try {
		            // Add your data
		            List<NameValuePair> params = new ArrayList<NameValuePair>(4);
		            params.add(new BasicNameValuePair("[account][display_name]", inputs[0]));
		            params.add(new BasicNameValuePair("[account][full_name]", inputs[1]));
		            params.add(new BasicNameValuePair("[account][balance]", inputs[2]));
		            params.add(new BasicNameValuePair("[account][interest_rate]", inputs[3]));
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
					Intent intent = new Intent(context, AccountsOverviewActivity.class);
					intent.putExtra("auth_token", auth_token);
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
