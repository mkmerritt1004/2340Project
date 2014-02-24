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
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends Activity {
	
	Button registerButton;
	EditText name;
	EditText email;
	EditText password;
	EditText passwordConfirmation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

    public void updateTextView(String newStr) {

        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText(newStr);
    }
    
    public void addListenerOnButton() {
		 
		final Context context = this;
 
		registerButton = (Button) findViewById(R.id.button1);
 
		registerButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				name   = (EditText)findViewById(R.id.editText1);
				email   = (EditText)findViewById(R.id.editText2);
				password   = (EditText)findViewById(R.id.editText3);
				passwordConfirmation   = (EditText)findViewById(R.id.editText4);
				String nameStr = name.getText().toString();
				String emailStr = email.getText().toString();
				String passwordStr = password.getText().toString();
				String passwordConfirmationStr = passwordConfirmation.getText().toString();

				new RegisterTask(context).execute(nameStr, emailStr, passwordStr, passwordConfirmationStr);
				
			}
 
		});
    
	}
    
	class RegisterTask extends AsyncTask<String, Void, HttpResponse> {

		private Context context;
		private HttpResponse response;
		
		private RegisterTask(Context context) {
		    this.context = context.getApplicationContext();
		}

	    protected HttpResponse doInBackground(String... inputs) {
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://intense-garden-9893.herokuapp.com/api/users.json");

	        try {
	            // Add your data
	            List<NameValuePair> params = new ArrayList<NameValuePair>(4);
	            params.add(new BasicNameValuePair("[user][name]", inputs[0]));
	            params.add(new BasicNameValuePair("[user][email]", inputs[1]));
	            params.add(new BasicNameValuePair("[user][password]", inputs[2]));
	            params.add(new BasicNameValuePair("[user][password_confirmation]", inputs[3]));
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
				Intent intent = new Intent(context, SuccessScreenActivity.class);
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
